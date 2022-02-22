package com.prototype.restaurantsreview.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.*;


@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String userFirstName;
    private String userLastName;
    private String username;
    private String userEmail;
    private String userPassword;
    @Enumerated(EnumType.STRING)
    private ERole userRole;
    private Boolean locked=false;
    private Boolean enabled=false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Article> articles;

    public User(String firstName, String lastName, String email, String username, String password, ERole role) {
        this.userFirstName=firstName;
        this.userLastName=lastName;
        this.userEmail=email;
        this.username=username;
        this.userPassword=password;
        this.userRole=role;
    }

    public User() {

    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
