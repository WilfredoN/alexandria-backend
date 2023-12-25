package com.example.alexandria.service.dto;

import lombok.*;


@Builder
public record GroupDTO(
        Long id,
        String name
) {
}

