package com.prototype.restaurantsreview.controller;

import com.prototype.restaurantsreview.repository.ArticleRepository;
import com.prototype.restaurantsreview.repository.ConfirmationTokenRepository;
import com.prototype.restaurantsreview.repository.UserRepository;
import com.prototype.restaurantsreview.service.ArticleService;
import com.prototype.restaurantsreview.service.ReviewService;
import com.prototype.restaurantsreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;


@Controller
@RequestMapping(path="/admin")
public class AuthController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;


    @GetMapping("/")
    public String panel()
    {
        return "panel";
    }

    @GetMapping("/users")
    public String users(Model model)
    {
        model.addAttribute("users",userService.getUsers());
        return "panelusers";
    }

    @GetMapping("/articles")
    public String articles(Model model)
    {
        model.addAttribute("articles",articleService.getArticles());
        return "panelarticles";
    }

    @GetMapping("/history")
    public String history(Model model)
    {
        model.addAttribute("review",reviewService.getReview());
        return "panelhistory";
    }

}
