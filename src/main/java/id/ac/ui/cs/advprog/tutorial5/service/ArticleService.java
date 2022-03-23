package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Article;

public interface ArticleService {
    Iterable<Article> getListArticle();

    Article createArticle(Article article);

    Article getArticleById(int id);

    Article updateArticle(int id, Article article);

    void deleteArticleById(int id);
}
