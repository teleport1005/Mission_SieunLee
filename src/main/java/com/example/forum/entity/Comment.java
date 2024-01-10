package com.example.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String content;
    @Setter
    private String password;



    @Setter
    @ManyToOne
    private Article article;

    public Comment() {
    }

    public Comment(String content, Article article) {
        this.content = content;
        this.article = article;
    }


}
