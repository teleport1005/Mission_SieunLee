package com.example.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
public class PostType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String postType;

    @OneToMany(mappedBy = "postType", fetch = FetchType.EAGER)
    private List<Article> articles;

    @ManyToOne
    @Setter
    private Board board;

    public PostType() {
    }

    public PostType(Long id, String postType, List<Article> articles) {
        this.id = id;
        this.postType = postType;
        this.articles = articles;
    }
}
