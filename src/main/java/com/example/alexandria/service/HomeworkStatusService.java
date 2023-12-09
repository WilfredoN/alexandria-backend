package com.example.alexandria.service;

import com.example.alexandria.repository.HomeworkStatusRepository;
import com.example.alexandria.repository.entity.HomeworkStatus;
import com.example.alexandria.service.dto.HomeworkStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkStatusService {
    private final HomeworkStatusRepository homeworkStatusRepository;

    public HomeworkStatusDTO map(HomeworkStatus homeworkStatus) {
        return HomeworkStatusDTO.builder()
                .id(homeworkStatus.getId())
                .homework_id(homeworkStatus.getHomework().getId())
                .student_id(homeworkStatus.getStudent().getId())
                .is_done(homeworkStatus.isDone())
                .marked_at(homeworkStatus.getMarkedAt().toString())
                .build();
    }

    public List<HomeworkStatusDTO> findAll() {
        return homeworkStatusRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    public List<HomeworkStatusDTO> findByHomeworkIdAndDone(Long homeworkId, boolean isDone) {
        return homeworkStatusRepository.findByHomeworkIdAndIsDone(homeworkId, isDone).stream()
                .map(this::map)
                .toList();
    }

    public List<HomeworkStatusDTO> findByStudentId(Long studentId) {
        return homeworkStatusRepository.findByStudentId(studentId).stream()
                .map(this::map)
                .toList();
    }

    public List<HomeworkStatusDTO> findByHomeworkId(Long homeworkId) {
        return homeworkStatusRepository.findByHomeworkId(homeworkId).stream()
                .map(this::map)
                .toList();
    }

    public HomeworkStatusDTO markHomeworkAsDone(Long homeworkId, Long studentId) {
        HomeworkStatus homeworkStatus = homeworkStatusRepository.findByHomeworkIdAndIsDone(homeworkId, false)
                .stream()
                .filter(status -> status.getStudent().getId() == studentId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        homeworkStatus.setDone(true);
        homeworkStatusRepository.save(homeworkStatus);
        return map(homeworkStatus);
    }
}
