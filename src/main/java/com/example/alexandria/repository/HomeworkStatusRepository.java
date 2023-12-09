package com.example.alexandria.repository;

import com.example.alexandria.repository.entity.HomeworkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkStatusRepository extends JpaRepository<HomeworkStatus, Long> {
    List<HomeworkStatus> findByHomeworkIdAndIsDone(Long homeworkId, boolean isDone);

    List<HomeworkStatus> findByHomeworkId(Long homeworkId);

    List<HomeworkStatus> findByStudentId(Long studentId);
}
