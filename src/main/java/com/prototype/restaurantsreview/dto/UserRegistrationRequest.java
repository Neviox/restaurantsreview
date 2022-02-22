package com.prototype.restaurantsreview.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserRegistrationRequest {
        @NotBlank
        private String firstName;

        @NotEmpty
        private String lastName;

        @NotEmpty
        private String username;

        @NotEmpty
        private String password;

        @NotEmpty
        @Email
        private String email;


}
