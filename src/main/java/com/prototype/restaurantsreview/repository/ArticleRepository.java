package com.prototype.restaurantsreview.repository;

import com.prototype.restaurantsreview.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findAllByArticleName(String name);
    void deleteArticleByArticleName(String name);
    Article findByArticleName(String articleName);
}
