package com.example.alexandria.service.dto;

import lombok.Builder;

@Builder
public record HomeworkStatusDTO(
        long id,
        long homework_id,
        long student_id,
        boolean is_done,
        String marked_at
) {
}
