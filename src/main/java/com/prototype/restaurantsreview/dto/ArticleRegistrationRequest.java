package com.prototype.restaurantsreview.dto;

import com.prototype.restaurantsreview.model.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ArticleRegistrationRequest {
    @NotBlank
    private String articleName;
    @NotEmpty
    private String articleDescription;
    @NotEmpty
    private String url;
    @NotEmpty
    private User createdBy;

}
