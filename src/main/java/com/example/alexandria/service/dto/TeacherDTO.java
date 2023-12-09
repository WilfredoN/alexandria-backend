package com.example.alexandria.service.dto;

import lombok.Builder;

@Builder
public record TeacherDTO(
        long id,
        String full_name,
        boolean is_admin,
        String login,
        String password) {

}
