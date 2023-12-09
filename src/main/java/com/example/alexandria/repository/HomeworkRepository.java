package com.example.alexandria.repository;

import com.example.alexandria.repository.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    Optional<Homework> findHomeworkById(Long id);

    Optional<Homework> findHomeworkByTitle(String title);
}
