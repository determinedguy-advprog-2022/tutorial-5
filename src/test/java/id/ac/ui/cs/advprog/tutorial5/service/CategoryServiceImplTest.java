package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Category;
import id.ac.ui.cs.advprog.tutorial5.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        String categoryName = "Action";
        int categoryNumArticles = 0;
        category = new Category(categoryName, categoryNumArticles);
        category.setId(0);
    }

    @Test
    public void testGetCategoryById() {
        lenient().when(categoryService.getCategoryById(0)).thenReturn(category);
        Category calledCategory = categoryService.getCategoryById(0);
        assertEquals(calledCategory.getId(), category.getId());
    }

    @Test
    public void testGetListCategory() {
        Iterable<Category> categoryList = categoryRepository.findAll();
        lenient().when(categoryService.getListCategory()).thenReturn(categoryList);
        Iterable<Category> categoryListResult = categoryService.getListCategory();
        assertIterableEquals(categoryList, categoryListResult);
    }

    @Test
    public void testCreateCategory() {
        lenient().when(categoryService.createCategory(category)).thenReturn(category);
    }

    @Test
    public void testUpdateCategory() {
        categoryService.createCategory(category);
        String pastName = category.getName();

        String newName = "Fantasy";
        category.setName(newName);

        lenient().when(categoryService.updateCategory(category.getId(), category)).thenReturn(category);
        Category categoryResult = categoryService.updateCategory(category.getId(), category);

        assertEquals(categoryResult.getId(), category.getId());
        assertNotEquals(categoryResult.getName(), pastName);
    }

    @Test
    public void testDeleteCategoryById() {
        categoryService.createCategory(category);
        lenient().when(categoryRepository.findById(0)).thenReturn(category);
        categoryService.deleteCategoryById(0);
        lenient().when(categoryService.getCategoryById(0)).thenReturn(null);
        assertNull(categoryService.getCategoryById(0));
    }

    @Test
    public void testDeleteNonExistentCategoryById() {
        categoryService.createCategory(category);
        lenient().when(categoryRepository.findById(0)).thenReturn(null);
        categoryService.deleteCategoryById(0);
    }
}
