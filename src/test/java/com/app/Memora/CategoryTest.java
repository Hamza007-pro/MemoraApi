package com.app.Memora;


import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.categorie.dtos.CategoryReadDTO;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.categorie.repositories.CategoryRepository;
import com.app.Memora.categorie.services.CategoryServiceImpl;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.deck.services.DeckService;
import com.app.Memora.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private UserService userService;

    @Mock
    private DeckService deckService;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the current user
        User currentUser = new User();
        currentUser.setId(1L);  // Set the necessary fields for the User
        when(userService.getCurrentUser()).thenReturn(currentUser);
    }

    @Test
    void testCreateCategory() {
        Category category = new Category();
        category.setName("New Category");

        when(categoryRepository.existsByNameIgnoreCase(category.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryService.createCategory(category);

        assertNotNull(createdCategory);
        assertEquals("New Category", createdCategory.getName());
        verify(categoryRepository, times(1)).existsByNameIgnoreCase(category.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testCreateCategoryAlreadyExists() {
        Category category = new Category();
        category.setName("Existing Category");

        when(categoryRepository.existsByNameIgnoreCase(category.getName())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            categoryService.createCategory(category);
        });

        verify(categoryRepository, times(1)).existsByNameIgnoreCase(category.getName());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testUpdateCategory() {
        Long categoryId = 1L;
        Category updatedDetails = new Category();
        updatedDetails.setName("Updated Category");

        Category existingCategory = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category updatedCategory = categoryService.updateCategory(categoryId, updatedDetails);

        assertNotNull(updatedCategory);
        assertEquals("Updated Category", updatedCategory.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testUpdateCategoryNotFound() {
        Long categoryId = 1L;
        Category updatedDetails = new Category();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(categoryId, updatedDetails);
        });

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;

        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.getCategoryById(categoryId);

        assertNotNull(foundCategory);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetCategoryByIdNotFound() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(categoryId);
        });

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> allCategories = categoryService.getAllCategories();

        assertEquals(1, allCategories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testSearchCategories() {
        String query = "test";
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());

        when(categoryRepository.findByNameContainingIgnoreCase(query)).thenReturn(categories);

        List<Category> searchResults = categoryService.searchCategories(query);

        assertEquals(1, searchResults.size());
        verify(categoryRepository, times(1)).findByNameContainingIgnoreCase(query);
    }

    @Test
    void testAssignCategoryToDeck() {
        Long categoryId = 1L;
        Long deckId = 1L;
        Category category = new Category();
        Deck deck = new Deck();

        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));
        when(deckRepository.save(deck)).thenReturn(deck);

        categoryService.assignCategoryToDeck(categoryId, deckId);

        assertTrue(deck.getCategories().contains(category));
        verify(categoryService, times(1)).getCategoryById(categoryId);
        verify(deckRepository, times(1)).findById(deckId);
        verify(deckRepository, times(1)).save(deck);
    }

    @Test
    void testAssignCategoryToDeckCategoryNotFound() {
        Long categoryId = 1L;
        Long deckId = 1L;

        when(categoryService.getCategoryById(categoryId)).thenThrow(new ResourceNotFoundException("Category not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.assignCategoryToDeck(categoryId, deckId);
        });

        verify(categoryService, times(1)).getCategoryById(categoryId);
        verify(deckRepository, never()).findById(deckId);
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void testAssignCategoryToDeckDeckNotFound() {
        Long categoryId = 1L;
        Long deckId = 1L;
        Category category = new Category();

        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        when(deckRepository.findById(deckId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.assignCategoryToDeck(categoryId, deckId);
        });

        verify(categoryService, times(1)).getCategoryById(categoryId);
        verify(deckRepository, times(1)).findById(deckId);
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void testConvertToReadDTO() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        Deck deck = new Deck();
        deck.setId(1L);
        category.setDecks(List.of(deck));

        DeckReadDTO deckReadDTO = new DeckReadDTO();
        when(deckService.convertToReadDTO(any(Deck.class))).thenReturn(deckReadDTO);

        CategoryReadDTO dto = categoryService.convertToReadDTO(category);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test Category", dto.getName());
        assertEquals(1, dto.getDecks().size());
        verify(deckService, times(1)).convertToReadDTO(any(Deck.class));
    }
}
