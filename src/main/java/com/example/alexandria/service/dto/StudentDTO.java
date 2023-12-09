package com.example.alexandria.service.dto;

import lombok.Builder;

@Builder
public record StudentDTO(
        long id,
        String full_name,
        String login,
        String password,
        String group_name
) {
}
