package com.app.Memora.categorie.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.categorie.dtos.CategoryCreationDTO;
import com.app.Memora.categorie.dtos.CategoryReadDTO;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.categorie.services.CategoryService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody CategoryCreationDTO categoryCreationDTO) {
        log.info("Creating new category by: {}", userService.getCurrentUser());
        Category category = new Category();
        category.setName(categoryCreationDTO.getName());
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category created successfully", createdCategory));
    }

    @PostMapping("/{categoryId}/decks/{deckId}")
    public ResponseEntity<ApiResponse<Void>> assignCategoryToDeck(@PathVariable Long categoryId,
                                                                  @PathVariable Long deckId) {
        categoryService.assignCategoryToDeck(categoryId, deckId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category assigned to deck", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id,
                                                                @Valid @RequestBody CategoryCreationDTO categoryCreationDTO) {
        log.info("Updating category by: {}", userService.getCurrentUser());
        Category category = new Category();
        category.setName(categoryCreationDTO.getName());
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category updated successfully", updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category by: {}", userService.getCurrentUser());
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category deleted successfully", null));
    }

    @GetMapping
    public List<CategoryReadDTO> getAllCategories() {
        log.info("Getting all categories by: {}", userService.getCurrentUser());
        return categoryService.getAllCategories().stream()
                .map(categoryService::convertToReadDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        log.info("Getting category by: {}", userService.getCurrentUser());
        return ResponseEntity.ok(new ApiResponse<>(true, "Category fetched successfully", categoryService.getCategoryById(id)));
    }

    @GetMapping("/search")
    public List<CategoryReadDTO> searchCategories(@RequestParam String query) {
        log.info("Searching categories by: {}", userService.getCurrentUser());
        return categoryService.searchCategories(query).stream()
                .map(categoryService::convertToReadDTO)
                .collect(Collectors.toList());
    }

}
