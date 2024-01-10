package com.example.forum.dto;

import com.example.forum.entity.Article;
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
    private Long boardId;

    private List<CommentDto> commentDtoList = new ArrayList<>();

    public ArticleDto(Long id, String title, String contents, Long boardId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.boardId = boardId;
    }

    public ArticleDto(String title, String contents, Long boardId) {
        this.title = title;
        this.contents = contents;
        this.boardId = boardId;
    }

    public static ArticleDto fromEntity(Article article) {
        ArticleDto dto = new ArticleDto();
        dto.id = article.getId();
        dto.title = article.getTitle();
        dto.contents = article.getContents();
        dto.commentDtoList = new ArrayList<>();
        return dto;
    }
}

