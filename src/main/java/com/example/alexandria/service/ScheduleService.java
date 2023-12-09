package com.example.alexandria.service;

import com.example.alexandria.repository.ScheduleRepository;
import com.example.alexandria.repository.entity.Group;
import com.example.alexandria.repository.entity.Schedule;
import com.example.alexandria.repository.entity.Subject;
import com.example.alexandria.repository.entity.Teacher;
import com.example.alexandria.service.dto.ScheduleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleDTO findSchedule(Long id) {
        return scheduleRepository.findScheduleById(id)
                .map(this::map)
                .orElseThrow(() -> new RuntimeException("Schedule not found for id: " + id));
    }

    public List<ScheduleDTO> findSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public List<ScheduleDTO> findScheduleByGroupName(String groupName) {
        return scheduleRepository.findScheduleByGroupId_Name(groupName)
                .stream()
                .map(this::map)
                .toList();
    }

    public ScheduleDTO create(ScheduleDTO schedule) {
        var savedSchedule = scheduleRepository.save(Schedule.builder()
                .id(schedule.id())
                .dayOfWeek(schedule.day_of_week())
                .lessonNum(schedule.lesson_num())
                .weekType(schedule.week_type())
                .subject(Subject.builder().id(schedule.subject_id()).build())
                .group(Group.builder().id(schedule.group_id()).build())
                .teacher(Teacher.builder().id(schedule.teacher_id()).build())
                .build());
        return map(savedSchedule);
    }

    public void update(Long id, ScheduleDTO updatedSchedule) {
        Schedule scheduleToUpdate = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found for id: " + id));

        scheduleToUpdate.setSubject(Subject.builder().id(updatedSchedule.subject_id()).build());
        scheduleToUpdate.setGroup(Group.builder().id(updatedSchedule.group_id()).build());
        scheduleToUpdate.setTeacher(Teacher.builder().id(updatedSchedule.teacher_id()).build());
        scheduleToUpdate.setDayOfWeek(updatedSchedule.day_of_week());
        scheduleToUpdate.setLessonNum(updatedSchedule.lesson_num());
        scheduleToUpdate.setWeekType(updatedSchedule.week_type());

        scheduleRepository.save(scheduleToUpdate);
    }

    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO map(Schedule schedule) {
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .day_of_week(schedule.getDayOfWeek())
                .subject_id(schedule.getSubject().getId())
                .group_id(schedule.getGroup().getId())
                .teacher_id(schedule.getTeacher().getId())
                .week_type(schedule.getWeekType())
                .lesson_num(schedule.getLessonNum())
                .build();
    }
}
