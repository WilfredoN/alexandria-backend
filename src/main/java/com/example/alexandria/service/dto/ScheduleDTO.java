package com.example.alexandria.service.dto;


import lombok.*;

@Builder
public record ScheduleDTO(
        long id,
        long subject_id,
        long group_id,
        long teacher_id,
        String day_of_week,
        int lesson_num,
        int week_type
) {}

