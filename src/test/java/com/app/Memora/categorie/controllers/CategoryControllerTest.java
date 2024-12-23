package com.app.Memora.categorie.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.categorie.dtos.CategoryCreationDTO;
import com.app.Memora.categorie.dtos.CategoryReadDTO;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.categorie.services.CategoryService;
import com.app.Memora.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;
    private CategoryCreationDTO categoryCreationDTO;
    private CategoryReadDTO categoryReadDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        categoryCreationDTO = new CategoryCreationDTO();
        categoryCreationDTO.setName("Test Category");

        categoryReadDTO = new CategoryReadDTO();
        categoryReadDTO.setId(1L);
        categoryReadDTO.setName("Test Category");
    }

    @Test
    void createCategory_ReturnsCreatedCategory() {
        // Arrange
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        // Act
        ResponseEntity<ApiResponse<Category>> response = categoryController.createCategory(categoryCreationDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Category created successfully", response.getBody().getMessage());
        assertEquals(category, response.getBody().getData());
        verify(categoryService).createCategory(any(Category.class));
    }

    @Test
    void assignCategoryToDeck_ReturnsSuccess() {
        // Arrange
        Long categoryId = 1L;
        Long deckId = 1L;
        doNothing().when(categoryService).assignCategoryToDeck(categoryId, deckId);

        // Act
        ResponseEntity<ApiResponse<Void>> response = categoryController.assignCategoryToDeck(categoryId, deckId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Category assigned to deck", response.getBody().getMessage());
        verify(categoryService).assignCategoryToDeck(categoryId, deckId);
    }

    @Test
    void updateCategory_ReturnsUpdatedCategory() {
        // Arrange
        when(categoryService.updateCategory(eq(1L), any(Category.class))).thenReturn(category);

        // Act
        ResponseEntity<ApiResponse<Category>> response = categoryController.updateCategory(1L, categoryCreationDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Category updated successfully", response.getBody().getMessage());
        assertEquals(category, response.getBody().getData());
        verify(categoryService).updateCategory(eq(1L), any(Category.class));
    }

    @Test
    void deleteCategory_ReturnsSuccess() {
        // Arrange
        doNothing().when(categoryService).deleteCategory(1L);

        // Act
        ResponseEntity<ApiResponse<Void>> response = categoryController.deleteCategory(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Category deleted successfully", response.getBody().getMessage());
        verify(categoryService).deleteCategory(1L);
    }

    @Test
    void getAllCategories_ReturnsListOfCategories() {
        // Arrange
        List<Category> categories = Arrays.asList(category);
        when(categoryService.getAllCategories()).thenReturn(categories);
        when(categoryService.convertToReadDTO(any(Category.class))).thenReturn(categoryReadDTO);

        // Act
        List<CategoryReadDTO> result = categoryController.getAllCategories();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(categoryReadDTO, result.get(0));
        verify(categoryService).getAllCategories();
        verify(categoryService).convertToReadDTO(category);
    }

    @Test
    void getCategoryById_ReturnsCategory() {
        // Arrange
        when(categoryService.getCategoryById(1L)).thenReturn(category);

        // Act
        ResponseEntity<ApiResponse<Category>> response = categoryController.getCategoryById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Category fetched successfully", response.getBody().getMessage());
        assertEquals(category, response.getBody().getData());
        verify(categoryService).getCategoryById(1L);
    }

    @Test
    void searchCategories_ReturnsMatchingCategories() {
        // Arrange
        String query = "test";
        List<Category> categories = Arrays.asList(category);
        when(categoryService.searchCategories(query)).thenReturn(categories);
        when(categoryService.convertToReadDTO(any(Category.class))).thenReturn(categoryReadDTO);

        // Act
        List<CategoryReadDTO> result = categoryController.searchCategories(query);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(categoryReadDTO, result.get(0));
        verify(categoryService).searchCategories(query);
        verify(categoryService).convertToReadDTO(category);
    }
}