package com.example.alexandria.repository;

import com.example.alexandria.repository.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Optional<Announcement> findAnnouncementById(Long id);
    Optional<Announcement> findAnnouncementByTitle(String title);
}
