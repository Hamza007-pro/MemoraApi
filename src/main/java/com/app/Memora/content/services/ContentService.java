package com.app.Memora.content.services;

import com.app.Memora.content.entities.Content;

import java.time.LocalDateTime;
import java.util.List;

public interface ContentService {
    Content createContent(Content content);
    Content updateContent(Content content);
    void deleteContent(Long id);
    Content getContentById(Long id);
    List<Content> getContentsByDateRange(LocalDateTime start, LocalDateTime end);
    List<Content> getRecentlyModifiedContent(LocalDateTime since);
}
