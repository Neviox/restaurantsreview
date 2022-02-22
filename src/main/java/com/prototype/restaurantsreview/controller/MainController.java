package com.prototype.restaurantsreview.controller;


import com.prototype.restaurantsreview.dto.ReviewDto;
import com.prototype.restaurantsreview.dto.UserRegistrationRequest;
import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.model.Review;
import com.prototype.restaurantsreview.model.User;
import com.prototype.restaurantsreview.repository.ArticleRepository;
import com.prototype.restaurantsreview.repository.UserRepository;
import com.prototype.restaurantsreview.service.ArticleService;
import com.prototype.restaurantsreview.service.ReviewService;
import com.prototype.restaurantsreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ReviewService reviewService;



    @GetMapping("/")
    public String posts(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                        @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) throws SQLException, ClassNotFoundException {

        model.addAttribute("posts", articleService.getPage(pageNumber, size));

        return "index";
    }

    @PostMapping("/")
    public String save(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                       @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                       Model model) throws SQLException, ClassNotFoundException {

        model.addAttribute("posts", articleService.getPage(pageNumber, size));

        return "index";
    }

    @GetMapping("/search")
    public String searchByName(Model model, String keyword)
    {
        model.addAttribute("posts",articleRepository.findAllByArticleName(keyword));

        return "searchresult";
    }

    @GetMapping (value = "/article/{id}")
    public String info(@PathVariable int id, Model model)
    {
        int sum=0,avg=0;
        for(Review review:reviewService.listAll(id)){
            sum=sum+review.getStars();
        }
        if(sum!=0) {
            avg = sum / reviewService.listAll(id).size();
        }
        Article article=articleRepository.findById(id).get();
        article.setAvg(avg);
        model.addAttribute("articles", article);
        model.addAttribute("rating",reviewService.listAll(id));
        model.addAttribute("addrating",new ReviewDto());




        return "articleinfo";
    }

    @Transactional
    @PostMapping(value = "/articleinfosaved")
    public String articleInfoSaved(Article article,
                                   ReviewDto review,
                                   Model model,
                                   @AuthenticationPrincipal User user){
        article=articleRepository.findById(article.getId()).get();
        model.addAttribute("article",article);
        model.addAttribute("addrating",review);


        ReviewDto temp=new ReviewDto();
        temp.setArticle(article);
        temp.setComment(review.getComment());
        temp.setStars(review.getStars());
        temp.setReviewedBy(user);


        reviewService.saveReview(temp);

        return "articleinfosaved";
    }


}