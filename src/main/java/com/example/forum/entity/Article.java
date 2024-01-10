package com.example.forum.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
    @Setter
    @Lob
    private String contents;
    @Setter
    private String password;

    @ManyToOne
    @Setter
    private Board board;

    @ManyToOne
    @Setter
    private PostType postType;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    public Article(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }


}
