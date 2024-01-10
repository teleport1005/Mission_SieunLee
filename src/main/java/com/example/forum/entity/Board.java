package com.example.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String category;
    @Setter
    private String description;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Article> articleList;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<PostType> postTypeList;


    public Board(Long id, String category, List<Article> articleList) {
        this.id = id;
        this.category = category;
        this.articleList = articleList;
    }

    public Board() {
    }
}
