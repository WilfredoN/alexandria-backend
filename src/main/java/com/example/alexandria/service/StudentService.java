package com.example.alexandria.service;

import com.example.alexandria.repository.entity.Group;
import com.example.alexandria.repository.entity.Student;
import com.example.alexandria.repository.StudentRepository;
import com.example.alexandria.service.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupService groupService;

    public StudentDTO mapStudent(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .full_name(student.getFull_name())
                .login(student.getLogin())
                .group_name(student.getGroup_name())
                .password(student.getPassword())
                .build();
    }

    public StudentDTO logIn(StudentDTO student) {
        var foundStudent = studentRepository.findByLogin(student.login());
        if (foundStudent.isPresent() && foundStudent.get().checkPassword(student.password())) {
            return mapStudent(foundStudent.get());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Invalid login or password");
        }
    }

    public StudentDTO findStudent(long id) {
        return studentRepository.findById(id)
                .map(this::mapStudent)
                .orElseThrow();
    }

    public StudentDTO findStudentByLogin(String login) {
        return studentRepository.findByLogin(login)
                .map(this::mapStudent)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Student not found"));
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
        try {
            var studentToUpdate = studentRepository.findByLogin(login).orElseThrow();
            studentToUpdate.setPassword(student.password());
            studentRepository.save(studentToUpdate);
            new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public StudentDTO create(StudentDTO student) {
        String hashedPassword = BCrypt.hashpw(student.password(), BCrypt.gensalt());
        Group group = groupService.findGroup(student.group_name());
        if (group == null) {
            groupService.create(Group.builder()
                    .name(student.group_name())
                    .build());
        }
        var savedStudent = studentRepository.save(Student.builder()
                .id(student.id())
                .full_name(student.full_name())
                .login(student.login())
                .password(hashedPassword)
                .group_name(student.group_name())
                .build());
        return mapStudent(savedStudent);
    }
    public List<StudentDTO> createStudents(List<StudentDTO> students) {
        return students.stream()
                .map(this::create)
                .toList();
    }

}
