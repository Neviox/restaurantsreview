package com.prototype.restaurantsreview.model;

import lombok.Data;

import javax.persistence.*;
import javax.websocket.OnMessage;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private int stars;
    @Column
    private String comment;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "article_id")
    private Article article;

}
