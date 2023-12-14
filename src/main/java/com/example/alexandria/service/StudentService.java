package com.example.alexandria.service;

import com.example.alexandria.exception.UserNotFoundException;
import com.example.alexandria.repository.StudentRepository;
import com.example.alexandria.repository.entity.Group;
import com.example.alexandria.repository.entity.Student;
import com.example.alexandria.security.PasswordUtil;
import com.example.alexandria.service.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupService groupService;

    public StudentDTO mapStudent(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .full_name(student.getFullName())
                .login(student.getLogin())
                .group_name(student.getGroupName())
                .password(student.getPassword())
                .build();
    }

    public StudentDTO logIn(StudentDTO student) {
        var foundStudent = studentRepository.findByLogin(student.login())
                .orElseThrow(() -> new UserNotFoundException("Student not found with login: " + student.login()));
        if (PasswordUtil.checkPassword(student.password(), foundStudent.getPassword())) {
            return mapStudent(foundStudent);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
    }

    public StudentDTO findStudent(long id) {
        return studentRepository.findById(id)
                .map(this::mapStudent)
                .orElseThrow();
    }

    public List<StudentDTO> findStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapStudent)
                .toList();
    }

    public void delete(String login) {
        var student = studentRepository.findByLogin(login).orElseThrow();
        studentRepository.delete(student);
    }

    public void update(String login, StudentDTO student) {
        var studentToUpdate = studentRepository.findByLogin(login).orElseThrow();
        String hashedPassword = PasswordUtil.hashPassword(student.password());
        studentToUpdate.setLogin(student.login());
        studentToUpdate.setPassword(hashedPassword);
        studentRepository.save(studentToUpdate);
    }

    public StudentDTO create(StudentDTO student) {
        String hashedPassword = PasswordUtil.hashPassword(student.password());
        Group group = groupService.findGroup(student.group_name());
        if (group == null) {
            groupService.create(Group.builder()
                    .name(student.group_name())
                    .build());
        }
        var savedStudent = studentRepository.save(Student.builder()
                .id(student.id())
                .fullName(student.full_name())
                .login(student.login())
                .password(hashedPassword)
                .groupName(student.group_name())
                .build());
        return mapStudent(savedStudent);
    }

    public List<StudentDTO> createStudents(List<StudentDTO> students) {
        return students.stream()
                .map(this::create)
                .toList();
    }

}
