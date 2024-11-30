package com.app.Memora.categorie.services;

import com.app.Memora.categorie.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    List<Category> searchCategories(String query);
    void assignCategoryToDeck(Long categoryId, Long deckId);
    void removeCategoryFromDeck(Long categoryId, Long deckId);
}
