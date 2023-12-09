package com.example.alexandria.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Entity
@Table(name = "homework_status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HomeworkStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "homework_id")
    private Homework homework;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    private boolean isDone;
    @LastModifiedDate
    private Instant markedAt;
}
