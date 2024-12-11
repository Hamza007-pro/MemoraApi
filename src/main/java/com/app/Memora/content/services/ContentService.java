package com.app.Memora.content.services;

import com.app.Memora.content.dtos.ContentReadDTO;
import com.app.Memora.content.entities.Content;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContentService {
    Content saveContent(Content content);
    Optional<Content> getContentById(Long id);
    List<Content> getAllContents();
    Content updateContent(Long id, Content contentDetails);
    void deleteContent(Long id);
    ContentReadDTO convertToReadDTO(Content content);
}
