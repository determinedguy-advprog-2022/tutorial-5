package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Category;
import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;
import id.ac.ui.cs.advprog.tutorial5.repository.SubcategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class SubcategoryServiceImplTest {
    @Mock
    private SubcategoryRepository subcategoryRepository;

    @InjectMocks
    private SubcategoryServiceImpl subcategoryService;

    private Subcategory subcategory;

    @BeforeEach
    public void setUp() {
        String categoryName = "Stress";
        int categoryNumArticles = 0;
        Category mainCategory = new Category(categoryName, categoryNumArticles);
        String subcategoryName = "Fasilkom UI";
        int subcategoryNumArticles = 0;
        subcategory = new Subcategory(subcategoryName, mainCategory, subcategoryNumArticles);
        subcategory.setId(0);
    }

    @Test
    public void testGetSubcategoryById() {
        lenient().when(subcategoryService.getSubcategoryById(0)).thenReturn(subcategory);
        Subcategory calledSubcategory = subcategoryService.getSubcategoryById(0);
        assertEquals(calledSubcategory.getId(), subcategory.getId());
    }

    @Test
    public void testGetListSubcategory() {
        Iterable<Subcategory> subcategoryList = subcategoryRepository.findAll();
        lenient().when(subcategoryService.getListSubcategory()).thenReturn(subcategoryList);
        Iterable<Subcategory> subcategoryListResult = subcategoryService.getListSubcategory();
        assertIterableEquals(subcategoryList, subcategoryListResult);
    }

    @Test
    public void testCreateSubcategory() {
        lenient().when(subcategoryService.createSubcategory(subcategory)).thenReturn(subcategory);
    }

    @Test
    public void testUpdateSubcategory() {
        subcategoryService.createSubcategory(subcategory);
        String pastName = subcategory.getName();

        String newName = "Fantasy";
        subcategory.setName(newName);

        lenient().when(subcategoryService.updateSubcategory(subcategory.getId(), subcategory)).thenReturn(subcategory);
        Subcategory subcategoryResult = subcategoryService.updateSubcategory(subcategory.getId(), subcategory);

        assertEquals(subcategoryResult.getId(), subcategory.getId());
        assertNotEquals(subcategoryResult.getName(), pastName);
    }

    @Test
    public void testDeleteSubcategoryById() {
        subcategoryService.createSubcategory(subcategory);
        lenient().when(subcategoryRepository.findById(0)).thenReturn(subcategory);
        subcategoryService.deleteSubcategoryById(0);
        lenient().when(subcategoryService.getSubcategoryById(0)).thenReturn(null);
        assertNull(subcategoryService.getSubcategoryById(0));
    }

    @Test
    public void testDeleteNonExistentSubcategoryById() {
        subcategoryService.createSubcategory(subcategory);
        lenient().when(subcategoryRepository.findById(0)).thenReturn(null);
        subcategoryService.deleteSubcategoryById(0);
    }
}
