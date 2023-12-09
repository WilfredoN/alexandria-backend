package com.example.alexandria.controller;


import com.example.alexandria.service.TeacherService;
import com.example.alexandria.service.dto.GroupDTO;
import com.example.alexandria.service.dto.TeacherDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDTO> getTeacher() {
        return teacherService.findTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDTO findTeacherById(@PathVariable long id) {
        return teacherService.findTeacher(id);
    }

    @PostMapping("/login")
    public TeacherDTO logIn(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.logIn(teacherDTO);
    }

    @PostMapping("/create")
    public TeacherDTO createTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.create(teacherDTO);
    }

    @PutMapping("/{login}")
    public void updateTeacher(@PathVariable String login, @RequestBody TeacherDTO teacherDTO) {
        teacherService.update(login, teacherDTO);
    }

    @GetMapping("/{teacherId}/groups")
    public List<GroupDTO> getTeacherGroups(@PathVariable long teacherId) {
        return teacherService.getTeacherWithGroups(teacherId);
    }

    @GetMapping("/groups/{groupName}")
    public List<TeacherDTO> getTeacherByGroup(@PathVariable String groupName) {
        return teacherService.findTeacherByGroup(groupName);
    }

    @DeleteMapping("/{login}")
    public void deleteTeacher(@PathVariable String login) {
        teacherService.delete(login);
    }
}


