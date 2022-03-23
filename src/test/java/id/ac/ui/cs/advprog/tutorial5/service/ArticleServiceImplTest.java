package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Article;
import id.ac.ui.cs.advprog.tutorial5.model.Category;
import id.ac.ui.cs.advprog.tutorial5.model.Editor;
import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;
import id.ac.ui.cs.advprog.tutorial5.repository.ArticleRepository;
import id.ac.ui.cs.advprog.tutorial5.repository.EditorRepository;
import id.ac.ui.cs.advprog.tutorial5.repository.SubcategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private EditorRepository editorRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article article;
    private Editor author;
    private Subcategory subcategory0;
    private Subcategory subcategory1;
    private Subcategory subcategory2;

    @BeforeEach
    public void setUp() {
        String articleTitle = "Apakah Athal lulus Adpro?";
        String articleContent = "Dikabarkan Athal sedang gundah. Apakah dia bisa lulus adpro?";
        author = new Editor("Athal Ganteng", "athal@ganteng.xyz", 0);
        author.setId(0);
        subcategory0 = new Subcategory("Breaking News", new Category("Main", 0), 0);
        subcategory0.setId(0);
        subcategory1 = new Subcategory("Fasilkom UI", new Category("Education", 0), 0);
        subcategory1.setId(1);
        subcategory2 = new Subcategory("Mahasiswa", new Category("Person", 0), 0);
        subcategory2.setId(2);
        List<Subcategory> subcategoryList = Arrays.asList(subcategory0, subcategory1, subcategory2);
        article = new Article(articleTitle, author, subcategoryList, articleContent);
        article.setId(0);
        article.setCreatedAt(new Date());
        article.setLastUpdatedAt(new Date());
    }

    @Test
    public void testGetArticleById() {
        lenient().when(articleService.getArticleById(0)).thenReturn(article);
        Article calledArticle = articleService.getArticleById(0);
        assertEquals(calledArticle.getId(), article.getId());
    }

    @Test
    public void testGetListArticle() {
        Iterable<Article> articleList = articleRepository.findAll();
        lenient().when(articleService.getListArticle()).thenReturn(articleList);
        Iterable<Article> articleListResult = articleService.getListArticle();
        assertIterableEquals(articleList, articleListResult);
    }

    @Test
    public void testCreateArticle() {
        lenient().when(editorRepository.findById(0)).thenReturn(author);
        lenient().when(subcategoryRepository.findById(0)).thenReturn(subcategory0);
        lenient().when(subcategoryRepository.findById(1)).thenReturn(subcategory1);
        lenient().when(subcategoryRepository.findById(2)).thenReturn(subcategory2);
        lenient().when(articleService.createArticle(article)).thenReturn(article);
    }

    @Test
    public void testUpdateArticle() {
        lenient().when(editorRepository.findById(0)).thenReturn(author);
        lenient().when(subcategoryRepository.findById(0)).thenReturn(subcategory0);
        lenient().when(subcategoryRepository.findById(1)).thenReturn(subcategory1);
        lenient().when(subcategoryRepository.findById(2)).thenReturn(subcategory2);
        articleService.createArticle(article);

        String pastContent = article.getContent();

        String newContent = "Ternyata Athal lulus adpro! Alhamdulillah dia berhasil mendapatkan nilai A.";
        article.setContent(newContent);
        article.setLastUpdatedAt(new Date());

        lenient().when(articleRepository.findById(article.getId())).thenReturn(article);
        lenient().when(articleService.updateArticle(article.getId(), article)).thenReturn(article);
        Article articleResult = articleService.updateArticle(article.getId(), article);

        assertEquals(articleResult.getId(), article.getId());
        assertNotEquals(articleResult.getContent(), pastContent);
    }

    @Test
    public void testDeleteArticleById() {
        lenient().when(editorRepository.findById(0)).thenReturn(author);
        lenient().when(subcategoryRepository.findById(0)).thenReturn(subcategory0);
        lenient().when(subcategoryRepository.findById(1)).thenReturn(subcategory1);
        lenient().when(subcategoryRepository.findById(2)).thenReturn(subcategory2);
        articleService.createArticle(article);
        lenient().when(articleRepository.findById(0)).thenReturn(article);
        articleService.deleteArticleById(0);
        lenient().when(articleService.getArticleById(0)).thenReturn(null);
        assertNull(articleService.getArticleById(0));
    }

    @Test
    public void testDeleteNonExistentArticleById() {
        lenient().when(editorRepository.findById(0)).thenReturn(author);
        lenient().when(subcategoryRepository.findById(0)).thenReturn(subcategory0);
        lenient().when(subcategoryRepository.findById(1)).thenReturn(subcategory1);
        lenient().when(subcategoryRepository.findById(2)).thenReturn(subcategory2);
        articleService.createArticle(article);
        lenient().when(articleRepository.findById(0)).thenReturn(null);
        articleService.deleteArticleById(0);
    }
}
