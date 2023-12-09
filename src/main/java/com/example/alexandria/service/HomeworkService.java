package com.example.alexandria.service;


import com.example.alexandria.repository.HomeworkRepository;
import com.example.alexandria.repository.entity.Group;
import com.example.alexandria.repository.entity.Homework;
import com.example.alexandria.repository.entity.Subject;
import com.example.alexandria.repository.entity.Teacher;
import com.example.alexandria.service.dto.HomeworkDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class HomeworkService {
    private final HomeworkRepository homeworkRepository;

    public HomeworkDTO map(Homework homework) {
        return HomeworkDTO.builder()
                .id(homework.getId())
                .title(homework.getTitle())
                .description(homework.getDescription())
                .deadline(homework.getDeadline().toString())
                .subject_id(homework.getSubject().getId())
                .teacher_id(homework.getTeacher().getId())
                .group_id(homework.getGroup().getId())
                .build();
    }

    public HomeworkDTO findHomework(long id) {
        return homeworkRepository.findHomeworkById(id)
                .map(this::map)
                .orElseThrow();
    }

    public HomeworkDTO findHomeworkByTitle(String title) {
        return homeworkRepository.findHomeworkByTitle(title)
                .map(this::map)
                .orElseThrow();
    }

    public List<HomeworkDTO> findHomeworks() {
        return homeworkRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    public HomeworkDTO create(HomeworkDTO homeworkDTO) {
        var homework = homeworkRepository.save(Homework.builder()
                .title(homeworkDTO.title())
                .description(homeworkDTO.description())
                .deadline(Instant.parse(homeworkDTO.deadline()))
                .subject(Subject.builder().id(homeworkDTO.subject_id()).build())
                .teacher(Teacher.builder().id(homeworkDTO.teacher_id()).build())
                .group(Group.builder().id(homeworkDTO.group_id()).build())
                .build());
        return map(homework);
    }

    public HomeworkDTO update(Long id, HomeworkDTO updatedHomework) {
        Homework homeworkToUpdate = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found for id: " + id));

        homeworkToUpdate.setSubject(Subject.builder().id(updatedHomework.subject_id()).build());
        homeworkToUpdate.setGroup(Group.builder().id(updatedHomework.group_id()).build());
        homeworkToUpdate.setTeacher(Teacher.builder().id(updatedHomework.teacher_id()).build());
        homeworkToUpdate.setTitle(updatedHomework.title());
        homeworkToUpdate.setDescription(updatedHomework.description());
        homeworkToUpdate.setDeadline(Instant.parse(updatedHomework.deadline()));

        var savedHomework = homeworkRepository.save(homeworkToUpdate);
        return map(savedHomework);
    }
}
