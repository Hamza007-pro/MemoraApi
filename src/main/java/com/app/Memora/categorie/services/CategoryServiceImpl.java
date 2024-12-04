package com.app.Memora.categorie.services;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.categorie.repositories.CategoryRepository;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final DeckRepository deckRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        log.info("Creating new category: {} by user: {}",
                category.getName(), userService.getCurrentUser().getId());
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalStateException("Category with this name already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public Category getCategoryById(Long id) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public List<Category> searchCategories(String query) {
        return null;
    }

    @Override
    @Transactional
    public void assignCategoryToDeck(Long categoryId, Long deckId) {
        Category category = getCategoryById(categoryId);
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));

        deck.getCategories().add(category);
        deckRepository.save(deck);
    }

    @Override
    public void removeCategoryFromDeck(Long categoryId, Long deckId) {

    }

    // Other methods implementation...
}