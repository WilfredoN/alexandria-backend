package com.example.alexandria.service;


import com.example.alexandria.repository.entity.Announcement;
import com.example.alexandria.repository.AnnouncementRepository;
import com.example.alexandria.repository.entity.Teacher;
import com.example.alexandria.service.dto.AnnouncementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;


    public AnnouncementDTO mapAnnouncements(Announcement announcement) {
        return AnnouncementDTO.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .author_id(announcement.getAuthor_id().getId())
                .created_at(announcement.getPostedAt())
                .updated_at(announcement.getEditedAt())
                .build();
    }

    public AnnouncementDTO findAnnouncement(long id) {
        return announcementRepository.findById(id)
                .map(this::mapAnnouncements)
                .orElseThrow();
    }

    public AnnouncementDTO findAnnouncementByTitle(String title) {
        return announcementRepository.findAnnouncementByTitle(title)
                .map(this::mapAnnouncements)
                .orElseThrow();
    }

    public List<AnnouncementDTO> findAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(this::mapAnnouncements)
                .collect(toList());
    }

    public void delete(Long id) {
        var announce = announcementRepository.findAnnouncementById(id).orElseThrow();
        announcementRepository.delete(announce);
    }

    public void update(AnnouncementDTO announcement) {
        var announce = announcementRepository.findAnnouncementByTitle(announcement.title()).orElseThrow();
        announce.setTitle(announcement.title());
        announce.setContent(announcement.content());
        announcementRepository.save(announce);
    }

    public AnnouncementDTO create(AnnouncementDTO announcement) {
        var newAnnouncement = announcementRepository.save(Announcement.builder()
                .id(announcement.id())
                .title(announcement.title())
                .content(announcement.content())
                .author_id(Teacher.builder().id(announcement.author_id()).build())
                .build());
        return mapAnnouncements(newAnnouncement);
    }
}
