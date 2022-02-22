package com.prototype.restaurantsreview.service;

import com.prototype.restaurantsreview.dto.ArticleRegistrationRequest;
import com.prototype.restaurantsreview.model.Article;

import java.sql.SQLException;
import java.util.List;

public interface IArticleService {
    //void fromDbToRepo() throws SQLException, ClassNotFoundException;
    void saveArticle(ArticleRegistrationRequest articleRegistrationRequest);
    List<Article> getArticles();
    Article updateArticle(Article article, int articleId);
    void deleteArticleById(Integer articleId);
}
