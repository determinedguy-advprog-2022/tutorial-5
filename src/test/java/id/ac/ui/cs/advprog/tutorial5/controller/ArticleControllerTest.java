package id.ac.ui.cs.advprog.tutorial5.controller;

import id.ac.ui.cs.advprog.tutorial5.model.Article;
import id.ac.ui.cs.advprog.tutorial5.model.Category;
import id.ac.ui.cs.advprog.tutorial5.model.Editor;
import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;
import id.ac.ui.cs.advprog.tutorial5.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleController.class)
public class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private Article article;
    private final String articleTitle = "Tutorial 5 dikabarkan susah!";
    private final String articleContent = "Dikabarkan Tutorial 5 susah! Apakah para mahasiswa dapat mengerjakannya?";
    private final Editor author = new Editor("Athal Ganteng", "athal@ganteng.xyz", 0);
    private final List<Subcategory> subcategoryList = Arrays.asList(
            new Subcategory("Breaking News", new Category("Main", 0), 0),
            new Subcategory("Fasilkom UI", new Category("Education", 0), 0),
            new Subcategory("Mahasiswa", new Category("Person", 0), 0));

    @BeforeEach
    public void setUp() {
        article = new Article(articleTitle, author, subcategoryList, articleContent);
    }

    @Test
    public void testPostArticle() throws Exception {
        when(articleService.createArticle(article)).thenReturn(article);

        mockMvc.perform(post("/article")
                        .contentType(MediaType.APPLICATION_JSON).content(Mapper.mapToJson(article)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(articleTitle))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.content").value(articleContent))
                .andExpect(jsonPath("$.subcategoryList").isNotEmpty());
    }

    @Test
    public void testGetListArticle() throws Exception {
        Iterable<Article> categoriesList = List.of(article);
        when(articleService.getListArticle()).thenReturn(categoriesList);

        mockMvc.perform(get("/article").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(articleTitle))
                .andExpect(jsonPath("$[0].author").value(author))
                .andExpect(jsonPath("$[0].content").value(articleContent))
                .andExpect(jsonPath("$[0].subcategoryList").isNotEmpty());
    }

    @Test
    public void testGetNonExistentArticleById() throws Exception {
        mockMvc.perform(get("/article/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetArticleById() throws Exception {
        when(articleService.getArticleById(0)).thenReturn(article);

        mockMvc.perform(get("/article/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(articleTitle))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.content").value(articleContent))
                .andExpect(jsonPath("$.subcategoryList").isNotEmpty());
    }

    @Test
    public void testUpdateArticle() throws Exception {
        articleService.createArticle(article);
        String newArticleContent = "Ternyata ... memang tutorial 5 terbukti susah!";
        article.setContent(newArticleContent);
        when(articleService.updateArticle(0, article)).thenReturn(article);

        mockMvc.perform(put("/article/0")
                        .contentType(MediaType.APPLICATION_JSON).content(Mapper.mapToJson(article)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(articleTitle))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.content").value(newArticleContent))
                .andExpect(jsonPath("$.subcategoryList").isNotEmpty());
    }

    @Test
    public void testDeleteArticleById() throws Exception {
        articleService.createArticle(article);

        mockMvc.perform(delete("/article/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
