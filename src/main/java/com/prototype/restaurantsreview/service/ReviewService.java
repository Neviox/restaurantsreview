package com.prototype.restaurantsreview.service;

import com.prototype.restaurantsreview.dto.ReviewDto;
import com.prototype.restaurantsreview.model.User;
import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.model.Review;
import com.prototype.restaurantsreview.repository.ArticleRepository;
import com.prototype.restaurantsreview.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ArticleRepository articleRepository;

    public void saveReview(ReviewDto reviewDto){
        Review review=new Review();
        review.setComment(reviewDto.getComment());
        review.setStars(reviewDto.getStars());
        review.setUser(reviewDto.getReviewedBy());
        review.setArticle(reviewDto.getArticle());
        reviewRepository.save(review);
    }

    public List<Review> getReview()
    {
        return reviewRepository.findAll();
    }

    public void deleteReviewById(Integer reviewId)
    {
        reviewRepository.deleteById(reviewId);
    }

    public void fromDbToRepo() throws SQLException, ClassNotFoundException {
        String myDriver = "com.mysql.cj.jdbc.Driver";
        String myUrl = "jdbc:mysql://localhost:3306/reviewdb";
        Class.forName(myDriver);
        String query = "SELECT * FROM article";
        Connection conn = DriverManager.getConnection(myUrl, "root", "admin");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Review review=new Review();
            review.setId(rs.getInt("id"));
            review.setComment(rs.getString("comment"));
            review.setStars(rs.getInt("stars"));
            review.setUser(rs.getObject("user",User.class));
            review.setArticle(rs.getObject("article",Article.class));

            reviewRepository.save(review);
        }
        st.close();
    }
    public List<Review> listAll(int id) {
        List<Review> reviews = new ArrayList<>();
        articleRepository.findById(id).get().getReview().forEach(reviews::add);
        /*
        for (Review review : reviews) {
            System.out.println(review.getComment());
        }
         */

        return reviews;
    }
}
