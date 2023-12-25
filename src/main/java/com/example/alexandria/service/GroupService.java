package com.example.alexandria.service;


import com.example.alexandria.repository.entity.Group;
import com.example.alexandria.repository.GroupRepository;
import com.example.alexandria.repository.TeacherRepository;
import com.example.alexandria.service.dto.GroupDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;

    public Group findGroup(String name) {
        return groupRepository.findGroupByName(name)
                .orElse(null);
    }

    public GroupDTO findGroup(Long id) {
        return groupRepository.findGroupById(id)
                .map(this::mapGroup)
                .orElse(null);
    }

    public List<GroupDTO> findGroups() {
        return groupRepository.findAll().stream()
                .map(this::mapGroup)
                .toList();
    }

    private GroupDTO mapGroup(Group group) {
        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }

    public GroupDTO create(Group group) {
        var savedGroup = groupRepository.save(Group.builder()
                .name(group.getName())
                .build());
        return mapGroup(savedGroup);
    }

    public Group update(Group group) {
        return groupRepository.save(Group.builder()
                .id(group.getId())
                .name(group.getName())
                .build());
    }

    @Transactional
    public void assignGroupsToTeacher(long teacherId, List<Long> groupIds) {
        var teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));
        List<Group> groups = groupRepository.findAllById(groupIds);
        teacher.setGroups(groups);
        teacherRepository.save(teacher);

    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}

