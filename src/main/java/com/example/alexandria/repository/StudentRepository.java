package com.example.alexandria.repository;

import com.example.alexandria.repository.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findById(long id);
    Optional<Student> findByLogin(String login);
}

