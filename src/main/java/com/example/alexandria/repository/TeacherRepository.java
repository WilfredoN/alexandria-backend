package com.example.alexandria.repository;

import com.example.alexandria.repository.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findById(long id);

    Optional<Teacher> findByLogin(String login);

    @Query("SELECT t FROM Teacher t LEFT JOIN FETCH t.groups WHERE t.id = :teacherId")
    Optional<Teacher> findByIdWithGroups(long teacherId);

    @Query("SELECT t FROM Teacher t LEFT JOIN FETCH t.subjects WHERE t.id = :teacherId")
    Optional<Teacher> findByIdWithSubjects(@Param("teacherId") long teacherId);

}
