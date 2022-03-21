package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Article;
import id.ac.ui.cs.advprog.tutorial5.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Iterable<Article> getListArticle() {
        return articleRepository.findAll();
    }

    @Override
    public Article createArticle(Article article) {
        article.setCreatedAt(new Date());
        article.setLastUpdatedAt(new Date());
        articleRepository.save(article);
        return article;
    }

    @Override
    public Article getArticleById(int id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article updateArticle(int id, Article article) {
        // Get the old article to set created date
        Article oldArticle = getArticleById(id);

        article.setId(id);
        article.setCreatedAt(oldArticle.getCreatedAt());
        article.setLastUpdatedAt(new Date());

        articleRepository.save(article);
        return article;
    }

    @Override
    public void deleteArticleById(int id) {
        Article article = this.getArticleById(id);
        if(article != null)
            articleRepository.delete(article);
    }
}
