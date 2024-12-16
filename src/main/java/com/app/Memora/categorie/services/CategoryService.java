package com.app.Memora.categorie.services;

import com.app.Memora.categorie.dtos.CategoryReadDTO;
import com.app.Memora.categorie.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id,Category category);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    List<Category> searchCategories(String query);
    void assignCategoryToDeck(Long categoryId, Long deckId);
    void removeCategoryFromDeck(Long categoryId, Long deckId);
    CategoryReadDTO convertToReadDTO(Category category);
}
