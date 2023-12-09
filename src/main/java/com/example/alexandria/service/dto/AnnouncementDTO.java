package com.example.alexandria.service.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record AnnouncementDTO (
    long id,
    String title,
    String content,
    long author_id,
    Instant created_at,
    Instant updated_at
) {
}
