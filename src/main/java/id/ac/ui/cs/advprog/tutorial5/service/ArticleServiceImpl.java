package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Article;
import id.ac.ui.cs.advprog.tutorial5.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        articleRepository.save(article);
        return article;
    }

    @Override
    public Article getArticleById(int id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article updateArticle(int id, Article article) {
        article.setId(id);
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
