package com.prototype.restaurantsreview.model;

import lombok.Data;


import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String articleName;
    private String articleDescription;
    private String url;
    private int avg;


    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY)
    private Collection<Review> review;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
