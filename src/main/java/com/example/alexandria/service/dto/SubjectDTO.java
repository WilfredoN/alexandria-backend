package com.example.alexandria.service.dto;

import lombok.Builder;

@Builder
public record SubjectDTO(
        long id,
        String subject_name
) {
}
