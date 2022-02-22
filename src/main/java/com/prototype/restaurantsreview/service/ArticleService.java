package com.prototype.restaurantsreview.service;

import com.prototype.restaurantsreview.dto.ArticleRegistrationRequest;
import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.model.Review;
import com.prototype.restaurantsreview.model.User;
import com.prototype.restaurantsreview.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleService implements IArticleService {
    @Autowired
    ArticleRepository articleRepository;
/*
    public void fromDbToRepo() throws SQLException, ClassNotFoundException {
        String myDriver = "com.mysql.cj.jdbc.Driver";
        String myUrl = "jdbc:mysql://localhost:3306/reviewdb";
        Class.forName(myDriver);
        String query = "SELECT * FROM article";
        Connection conn = DriverManager.getConnection(myUrl, "root", "admin");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Article article=new Article();
            article.setId(rs.getInt("id"));
            article.setArticleName(rs.getString("article_name"));
            article.setArticleDescription(rs.getString("article_description"));
            article.setUrl(rs.getString("url"));

            articleRepository.save(article);
        }
        st.close();
    }

 */

    public com.prototype.restaurantsreview.model.paging.Paged<Article> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Article> postPage = articleRepository.findAll(request);
        return new com.prototype.restaurantsreview.model.paging.Paged<>(postPage,
                com.prototype.restaurantsreview.model.paging.Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public void saveArticle(ArticleRegistrationRequest articleRegistrationRequest){
        Article article=new Article();
        article.setArticleName(articleRegistrationRequest.getArticleName());
        article.setArticleDescription(articleRegistrationRequest.getArticleDescription());
        article.setUrl(articleRegistrationRequest.getUrl());
        article.setUser(articleRegistrationRequest.getCreatedBy());

        articleRepository.save(article);
    }

    public List<Article> getArticles()
    {
        return articleRepository.findAll();
    }

    public Article updateArticle(Article article, int articleId)
    {
        Article temp = articleRepository.findById(articleId).get();

        if (Objects.nonNull(article.getArticleName()) && !"".equalsIgnoreCase(article.getArticleName()))
        {temp.setArticleName(article.getArticleName());}

        if (Objects.nonNull(article.getArticleDescription()) && !"".equalsIgnoreCase(article.getArticleDescription()))
        {temp.setArticleDescription(article.getArticleDescription());}

        if (Objects.nonNull(article.getUrl()) && !"".equalsIgnoreCase(article.getUrl()))
        {temp.setUrl(article.getUrl());}


        return articleRepository.save(temp);
    }

    public void deleteArticleById(Integer articleId)
    {
        articleRepository.deleteById(articleId);
    }
}
