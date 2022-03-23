package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Article;
import id.ac.ui.cs.advprog.tutorial5.model.Category;
import id.ac.ui.cs.advprog.tutorial5.model.Editor;
import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;
import id.ac.ui.cs.advprog.tutorial5.repository.ArticleRepository;
import id.ac.ui.cs.advprog.tutorial5.repository.EditorRepository;
import id.ac.ui.cs.advprog.tutorial5.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private EditorRepository editorRepository;

    @Override
    public Iterable<Article> getListArticle() {
        return articleRepository.findAll();
    }

    @Override
    public Article createArticle(Article article) {
        article.setCreatedAt(new Date());
        article.setLastUpdatedAt(new Date());

        // Configure author
        Editor author = editorRepository.findById(article.getAuthor().getId());
        author.setWrittenArticles(author.getWrittenArticles()+1);
        article.setAuthor(author);

        // Configure subcategories
        for (Subcategory subcategory : article.getSubcategoryList()) {
            Subcategory newSubcategory = subcategoryRepository.findById(subcategory.getId());
            newSubcategory.setNumArticles(newSubcategory.getNumArticles()+1);
            subcategory.setMainCategory(newSubcategory.getMainCategory());
            subcategory.setName(newSubcategory.getName());
            subcategory.setNumArticles(newSubcategory.getNumArticles());

            Category mainCategory = subcategory.getMainCategory();
            mainCategory.setNumArticles(mainCategory.getNumArticles()+1);
        }

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

        // Configure author
        Editor author = editorRepository.findById(oldArticle.getAuthor().getId());
        article.setAuthor(author);

        // Configure subcategories
        // Assume that subcategories are immutable once created
        List<Subcategory> subcategoryList = oldArticle.getSubcategoryList();
        article.setSubcategoryList(subcategoryList);

        articleRepository.save(article);
        return article;
    }

    @Override
    public void deleteArticleById(int id) {
        Article article = this.getArticleById(id);
        if(article != null) {
            // Configure subcategories
            for (Subcategory subcategory : article.getSubcategoryList()) {
                Subcategory newSubcategory = subcategoryRepository.findById(subcategory.getId());
                newSubcategory.setNumArticles(newSubcategory.getNumArticles()-1);
                Category mainCategory = subcategory.getMainCategory();
                mainCategory.setNumArticles(mainCategory.getNumArticles()-1);
            }

            // Configure author
            Editor author = editorRepository.findById(article.getAuthor().getId());
            author.setWrittenArticles(author.getWrittenArticles()-1);

            articleRepository.delete(article);
        }
    }
}
