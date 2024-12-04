package com.app.Memora.content.services;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.repositories.ContentRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Content saveContent(Content content) {
        return contentRepository.save(content);
    }

    @Override
    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    @Override
    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    @Override
    public Content updateContent(Long id, Content contentDetails) {
        Content content = contentRepository.findById(id).orElseThrow();
        content.setImage(contentDetails.getImage());
        content.setQuestion(contentDetails.getQuestion());
        content.setAnswer(contentDetails.getAnswer());
        content.setDateModified(LocalDateTime.now());
        return contentRepository.save(content);
    }

    @Override
    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }
    // Other methods implementation...
}
