package com.example.alexandria.service.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TeacherDTO(
        long id,
        String full_name,
        boolean is_admin,
        String login,
        String password,
        List<GroupDTO> groups,
        List<SubjectDTO> subjects) {

}
