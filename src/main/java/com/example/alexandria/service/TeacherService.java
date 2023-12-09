package com.example.alexandria.service;

import com.example.alexandria.repository.TeacherRepository;
import com.example.alexandria.repository.entity.Teacher;
import com.example.alexandria.service.dto.GroupDTO;
import com.example.alexandria.service.dto.TeacherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherDTO mapTeacher(Teacher teacher) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .full_name(teacher.getFull_name())
                .login(teacher.getLogin())
                .is_admin(teacher.is_admin())
                .password(teacher.getPassword())
                .build();
    }

    public TeacherDTO logIn(TeacherDTO teacher) {
        var foundTeacher = teacherRepository.findByLogin(teacher.login());
        if (foundTeacher.isPresent() && foundTeacher.get().checkPassword(teacher.password())) {
            return mapTeacher(foundTeacher.get());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Invalid login or password");
        }
    }

    public List<GroupDTO> getTeacherWithGroups(long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByIdWithGroups(teacherId);
        List<GroupDTO> groupDTOs = new ArrayList<>();
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            groupDTOs = teacher.getGroups().stream()
                    .map(group -> {
                        GroupDTO dto = new GroupDTO();
                        dto.setId(group.getId());
                        dto.setName(group.getName());
                        return dto;
                    })
                    .toList();
        }
        return groupDTOs;
    }


    public TeacherDTO findTeacher(long id) {
        return teacherRepository.findById(id)
                .map(this::mapTeacher)
                .orElseThrow();
    }
    public List<TeacherDTO> findTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::mapTeacher)
                .toList();
    }

    public List<TeacherDTO> findTeacherByGroup(String groupName) {
        List<Teacher> teachers = teacherRepository.findByGroupsName(groupName);
        return teachers.stream()
                .map(this::mapTeacher)
                .toList();
    }

    public void delete(String login) {
        var teacher = teacherRepository.findByLogin(login).orElseThrow();
        teacherRepository.delete(teacher);
    }

    public void update(String login, TeacherDTO teacher) {
        var teacherToUpdate = teacherRepository.findByLogin(login).orElseThrow();
        teacherToUpdate.setPassword(teacher.password());
        teacherToUpdate.set_admin(teacher.is_admin());
        teacherRepository.save(teacherToUpdate);
    }

    public TeacherDTO create(TeacherDTO teacher) {
        String hashedPassword = BCrypt.hashpw(teacher.password(), BCrypt.gensalt());
        var savedTeacher = teacherRepository.save(Teacher.builder()
                .full_name(teacher.full_name())
                .login(teacher.login())
                .is_admin(teacher.is_admin())
                .password(hashedPassword)
                .build());
        return mapTeacher(savedTeacher);
    }
}

