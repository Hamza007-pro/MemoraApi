package com.app.Memora.content.services;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.repositories.ContentRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Content createContent(Content content) {
        log.info("Creating new content by user: {}", userService.getCurrentUser().getId());
        content.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
        content.setDateModified(LocalDateTime.now(ZoneOffset.UTC));
        return contentRepository.save(content);
    }

    @Override
    @Transactional
    public Content updateContent(Content content) {
        log.info("Updating content ID: {} by user: {}", content.getId(), userService.getCurrentUser().getId());
        return contentRepository.findById(content.getId())
                .map(existingContent -> {
                    existingContent.setImage(content.getImage());
                    existingContent.setQuestion(content.getQuestion());
                    existingContent.setAnswer(content.getAnswer());
                    existingContent.setDateModified(LocalDateTime.now(ZoneOffset.UTC));
                    return contentRepository.save(existingContent);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));
    }

    @Override
    public void deleteContent(Long id) {

    }

    @Override
    public Content getContentById(Long id) {
        return null;
    }

    @Override
    public List<Content> getContentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Content> getRecentlyModifiedContent(LocalDateTime since) {
        return null;
    }

    // Other methods implementation...
}
