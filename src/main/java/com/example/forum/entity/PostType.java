package com.example.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
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

}
