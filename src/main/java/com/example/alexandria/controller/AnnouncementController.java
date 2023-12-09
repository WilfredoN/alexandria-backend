package com.example.alexandria.controller;

import com.example.alexandria.service.dto.AnnouncementDTO;
import com.example.alexandria.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping
    public List<AnnouncementDTO> getAnnouncements(
    ) {
        return announcementService.findAnnouncements();
    }

    @GetMapping("/{id}")
    public AnnouncementDTO getAnnouncement(@PathVariable long id) {
        return announcementService.findAnnouncement(id);
    }

    @PostMapping("/create")
    public AnnouncementDTO createAnnouncement(@RequestBody AnnouncementDTO announcement) {
        return announcementService.create(announcement);
    }

    @PutMapping("/update")
    public void updateAnnouncement(@RequestBody AnnouncementDTO announcement) {
        announcementService.update(announcement);
    }
    @DeleteMapping("/{id}")
    public void deleteAnnouncement(@PathVariable long id) {
        announcementService.delete(id);
    }
}
