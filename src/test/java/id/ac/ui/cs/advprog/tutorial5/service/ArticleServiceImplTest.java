package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Article;
import id.ac.ui.cs.advprog.tutorial5.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article article;

    @BeforeEach
    public void setUp() {
        String articleTitle = "Apakah Athal lulus Adpro?";
        int authorId = 69;
        String articleContent = "Dikabarkan Athal sedang gundah. Apakah dia bisa lulus adpro?";
        article = new Article(articleTitle, authorId, articleContent);
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
        lenient().when(articleService.createArticle(article)).thenReturn(article);
    }

    @Test
    public void testUpdateArticle() {
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
        articleService.createArticle(article);
        lenient().when(articleRepository.findById(0)).thenReturn(article);
        articleService.deleteArticleById(0);
        lenient().when(articleService.getArticleById(0)).thenReturn(null);
        assertNull(articleService.getArticleById(0));
    }

    @Test
    public void testDeleteNonExistentArticleById() {
        articleService.createArticle(article);
        lenient().when(articleRepository.findById(0)).thenReturn(null);
        articleService.deleteArticleById(0);
    }
}
