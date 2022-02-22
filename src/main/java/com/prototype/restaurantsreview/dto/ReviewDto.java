package com.prototype.restaurantsreview.dto;

import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.model.User;
import lombok.Data;

@Data
public class ReviewDto {
    String comment;
    int stars;
    User reviewedBy;
    Article article;
}
