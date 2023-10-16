package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.CategoryAlreadyExitsException;
import br.com.wswork.bestcommerceapi.exception.CategoryNotFoundException;
import br.com.wswork.bestcommerceapi.model.Category;
import br.com.wswork.bestcommerceapi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        // Configurar comportamento do repositório simulado (mock)
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(
                new Category(1L, "Electronics"),
                new Category(2L, "Clothing")
        ));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(
                new Category(1L, "Electronics")
        ));

        when(categoryRepository.findById(3L)).thenReturn(Optional.empty());

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(
                new Category(1L, "Electronics")
        ));
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        assertEquals(2, categories.size());
    }

    @Test
    public void testGetCategoryById() {
        Category category = categoryService.getCategoryById(1L);
        assertNotNull(category);
        assertEquals(1L, category.getId());
    }

    @Test
    public void testGetCategoryById_WhenCategoryNotFound() {
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(3L));
    }

    @Test
    public void testAddCategory() {
        Category newCategory = new Category(null,"Books");
        when(categoryRepository.findByName("Books")).thenReturn(Optional.empty());

        Category addedCategory = categoryService.addCategory(newCategory);

        assertNotNull(addedCategory);
        assertEquals("Books", addedCategory.getName());
    }

    @Test
    public void testAddCategory_WhenCategoryAlreadyExists() {
        Category existingCategory = new Category(null,"Electronics");
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(existingCategory));

        assertThrows(CategoryAlreadyExitsException.class, () -> categoryService.addCategory(existingCategory));
    }
}