package com.example.alexandria.controller;

import com.example.alexandria.repository.entity.Group;
import com.example.alexandria.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public List<Group> getGroups(
    ) {
        return groupService.findGroups();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable long id) {
        return groupService.findGroup(id);
    }

    @PostMapping("/create")
    public Group createGroup(@RequestBody Group group) {
        return groupService.create(group);
    }

    @PostMapping("/assign/{teacherId}")
    public void assignGroups(@PathVariable long teacherId, @RequestBody List<Long> groupIds) {
        groupService.assignGroupsToTeacher(teacherId, groupIds);
    }
}
