package com.example.alexandria.service;

import com.example.alexandria.exception.UserNotFoundException;
import com.example.alexandria.repository.TeacherRepository;
import com.example.alexandria.repository.entity.Teacher;
import com.example.alexandria.security.PasswordUtil;
import com.example.alexandria.service.dto.GroupDTO;
import com.example.alexandria.service.dto.SubjectDTO;
import com.example.alexandria.service.dto.TeacherDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherDTO mapTeacher(Teacher teacher) {
        List<GroupDTO> groupDTOs = teacher.getGroups() != null ? teacher.getGroups().stream().map(group -> GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .build()).toList() : new ArrayList<>();

        List<SubjectDTO> subjectDTOs = teacher.getSubjects() != null ? teacher.getSubjects().stream().map(subject -> SubjectDTO.builder()
                .id(subject.getId())
                .subject_name(subject.getSubjectName())
                .build()).toList() : new ArrayList<>();

        return TeacherDTO.builder()
                .id(teacher.getId())
                .full_name(teacher.getFullName())
                .login(teacher.getLogin())
                .is_admin(teacher.isAdmin())
                .password(teacher.getPassword())
                .groups(groupDTOs)
                .subjects(subjectDTOs)
                .build();
    }

    public TeacherDTO logIn(TeacherDTO teacher) {
        var foundTeacher = teacherRepository.findByLogin(teacher.login())
                .orElseThrow(() -> new UserNotFoundException("Teacher not found with login: " + teacher.login()));
        log.info("Teacher found: {} {} {}", foundTeacher.getId(), foundTeacher.getLogin(), foundTeacher.getPassword());
        log.info("Teacher password: {}", teacher.password());
        if (PasswordUtil.checkPassword(teacher.password(), foundTeacher.getPassword())) {
            log.warn("Teacher authenticated: {}, Teacher password: {}", foundTeacher.getLogin(), foundTeacher.getPassword());
            return mapTeacher(foundTeacher);
        } else {
            log.warn("Teacher not authenticated: {} {}, Teacher password: {}", foundTeacher.getId(), foundTeacher.getLogin(), foundTeacher.getPassword());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
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

    public void delete(String login) {
        var teacher = teacherRepository.findByLogin(login).orElseThrow();
        teacherRepository.delete(teacher);
    }

    public void update(String login, TeacherDTO teacher) {
        var teacherToUpdate = teacherRepository.findByLogin(login).orElseThrow();
        if (teacher.password() != null && !teacher.password().isEmpty()) {
            log.info("Password has been changed");
            String hashedPassword = PasswordUtil.hashPassword(teacher.password());
            teacherToUpdate.setPassword(hashedPassword);
        }
        else {
            log.warn("Password has not been changed");
        }
        teacherToUpdate.setAdmin(teacher.is_admin());
        teacherRepository.save(teacherToUpdate);
    }

    public TeacherDTO create(TeacherDTO teacher) {
        String hashedPassword = PasswordUtil.hashPassword(teacher.password());
        var savedTeacher = teacherRepository.save(Teacher.builder()
                .fullName(teacher.full_name())
                .login(teacher.login())
                .isAdmin(teacher.is_admin())
                .password(hashedPassword)
                .build());
        return mapTeacher(savedTeacher);
    }

    public List<GroupDTO> findGroupsByTeacher(long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByIdWithGroups(teacherId);
        List<GroupDTO> groupDTOs = new ArrayList<>();
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            groupDTOs = teacher.getGroups().stream()
                    .map(group -> GroupDTO.builder()
                            .id(group.getId())
                            .name(group.getName())
                            .build())
                    .toList();
        }
        return groupDTOs;
    }

    public List<SubjectDTO> findSubjectsByTeacher(long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByIdWithSubjects(teacherId);
        List<SubjectDTO> subjectDTOs = new ArrayList<>();
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            subjectDTOs = teacher.getSubjects().stream()
                    .map(subject -> SubjectDTO.builder()
                            .id(subject.getId())
                            .subject_name(subject.getSubjectName())
                            .build())
                    .toList();
        }
        return subjectDTOs;
    }

}

