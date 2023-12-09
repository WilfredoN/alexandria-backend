package com.example.alexandria.controller;

import com.example.alexandria.service.dto.StudentDTO;
import com.example.alexandria.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<StudentDTO> getStudents(
            @RequestParam(required = false) String full_name,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String group_name
    ) {
        log.info("getStudents: full_name={}, login={}, password={}, group_name={}, "
                , full_name, login, password, group_name);
        return studentService.findStudents();
    }

    @GetMapping("/{id}")
    public StudentDTO findStudentById(@PathVariable long id) {
        return studentService.findStudent(id);
    }

    @PostMapping("/login")
    public StudentDTO login(@RequestBody StudentDTO studentDTO) {
        return studentService.logIn(studentDTO);
    }

    @PostMapping("/create")
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.create(studentDTO);
    }

    @PostMapping("/createMany")
    public List<StudentDTO> createStudents(@RequestBody List<StudentDTO> studentDTOs) {
        return studentService.createStudents(studentDTOs);
    }

    @PutMapping("/{login}")
    public void updateStudent(@PathVariable String login, @RequestBody StudentDTO studentDTO) {
        studentService.update(login, studentDTO);

    }

    @DeleteMapping("/{login}")
    public void deleteStudent(@PathVariable String login) {
        studentService.delete(login);
    }
}

