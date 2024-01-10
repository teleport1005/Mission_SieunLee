package com.example.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private Long boardId;
    private String password;

    private final List<CommentDto> commentDtoList = new ArrayList<>();

    public ArticleDto(String title, String contents, String username, String password) {
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.password = password;
    }
}

