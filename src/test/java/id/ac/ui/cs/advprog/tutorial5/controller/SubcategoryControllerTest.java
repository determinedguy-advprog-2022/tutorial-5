package id.ac.ui.cs.advprog.tutorial5.controller;

import id.ac.ui.cs.advprog.tutorial5.model.Category;
import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;
import id.ac.ui.cs.advprog.tutorial5.service.SubcategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SubcategoryController.class)
public class SubcategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubcategoryService subcategoryService;

    private Subcategory subcategory;
    private final String subcategoryName = "Fasilkom UI";
    private final int subcategoryNumArticles = 0;

    private Category mainCategory;
    private final String categoryName = "Stress";
    private final int categoryNumArticles = 0;

    @BeforeEach
    public void setUp() {
        mainCategory = new Category(categoryName, categoryNumArticles);
        subcategory = new Subcategory(subcategoryName, mainCategory, subcategoryNumArticles);
    }

    @Test
    public void testPostSubcategory() throws Exception {
        when(subcategoryService.createSubcategory(subcategory)).thenReturn(subcategory);

        mockMvc.perform(post("/subcategory")
                        .contentType(MediaType.APPLICATION_JSON).content(Mapper.mapToJson(subcategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(subcategoryName))
                .andExpect(jsonPath("$.numArticles").value(subcategoryNumArticles));
    }

    @Test
    public void testGetListSubcategory() throws Exception {
        Iterable<Subcategory> categoriesList = List.of(subcategory);
        when(subcategoryService.getListSubcategory()).thenReturn(categoriesList);

        mockMvc.perform(get("/subcategory").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(subcategoryName))
                .andExpect(jsonPath("$[0].numArticles").value(subcategoryNumArticles));
    }

    @Test
    public void testGetNonExistentSubcategoryById() throws Exception {
        mockMvc.perform(get("/subcategory/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetSubcategoryById() throws Exception {
        when(subcategoryService.getSubcategoryById(0)).thenReturn(subcategory);

        mockMvc.perform(get("/subcategory/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(subcategoryName));
    }

    @Test
    public void testUpdateSubcategory() throws Exception {
        subcategoryService.createSubcategory(subcategory);
        String newSubcategoryName = "Fantasy";
        subcategory.setName(newSubcategoryName);
        when(subcategoryService.updateSubcategory(0, subcategory)).thenReturn(subcategory);

        mockMvc.perform(put("/subcategory/0")
                        .contentType(MediaType.APPLICATION_JSON).content(Mapper.mapToJson(subcategory)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(newSubcategoryName));
    }

    @Test
    public void testDeleteSubcategoryById() throws Exception {
        subcategoryService.createSubcategory(subcategory);

        mockMvc.perform(delete("/subcategory/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
