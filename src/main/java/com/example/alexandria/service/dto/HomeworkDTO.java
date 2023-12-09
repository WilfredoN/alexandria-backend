package com.example.alexandria.service.dto;

import lombok.Builder;

@Builder
public record HomeworkDTO(
        long id,
        long subject_id,
        long group_id,
        long teacher_id,
        String title,
        String description,
        String deadline
) {
}
