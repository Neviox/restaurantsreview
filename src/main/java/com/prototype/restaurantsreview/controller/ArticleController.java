package com.prototype.restaurantsreview.controller;

import com.prototype.restaurantsreview.dto.ArticleRegistrationRequest;
import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.model.User;
import com.prototype.restaurantsreview.repository.ArticleRepository;
import com.prototype.restaurantsreview.repository.UserRepository;
import com.prototype.restaurantsreview.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/registerarticle")
    public String showRegistrationForm(Model model){
        model.addAttribute("article",new ArticleRegistrationRequest());
        return "articleregister";
    }

    @PostMapping("/registerarticle")
    public String registerArticle(Model model, ArticleRegistrationRequest article,
                                  @AuthenticationPrincipal User user){

        article.setCreatedBy(user);
        articleService.saveArticle(article);
        model.addAttribute("article",article);
        return "articlesaved";
    }

    @Transactional
    @RequestMapping(value = "/deletearticle/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, Model model)
    {
        articleService.deleteArticleById(id);
        model.addAttribute("article",articleService.getArticles());
        return "panel";
    }


    @Transactional
    @RequestMapping(value = "/updatearticle/{articleName}", method = RequestMethod.GET)
    public String update(@PathVariable String articleName,Model model)
    {
        model.addAttribute("articles", articleRepository.findByArticleName(articleName));
        model.addAttribute("newarticle", new Article());
        return "articleedit";
    }

    @Transactional
    @RequestMapping(value = "/updatearticle/{articleName}", method = RequestMethod.POST)
    public String updateDonation(@PathVariable String articleName,Model model,@ModelAttribute("articles") Article article)
    {
        Article temp=articleRepository.findByArticleName(articleName);
        int id=temp.getId();
        articleService.updateArticle(article,id);
        return "panelarticles";
    }
}
