package com.prototype.restaurantsreview.controller;

import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;

@Controller
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Transactional
    @RequestMapping(value = "/historydelete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, Model model)
    {
        reviewService.deleteReviewById(id);
        model.addAttribute("review",reviewService.getReview());
        return "panel";
    }

}
