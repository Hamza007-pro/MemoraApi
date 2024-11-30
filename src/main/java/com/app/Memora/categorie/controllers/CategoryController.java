package com.app.Memora.categorie.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.categorie.services.CategoryService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody Category category) {
        log.info("Creating new category by: {}", userService.getCurrentUser());
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category created successfully", createdCategory));
    }

    @PostMapping("/{categoryId}/decks/{deckId}")
    public ResponseEntity<ApiResponse<Void>> assignCategoryToDeck(@PathVariable Long categoryId,
                                                                  @PathVariable Long deckId) {
        categoryService.assignCategoryToDeck(categoryId, deckId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category assigned to deck", null));
    }
}
