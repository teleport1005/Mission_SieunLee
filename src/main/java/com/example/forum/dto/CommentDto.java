package com.example.forum.dto;

import com.example.forum.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @Setter
    private String content;


    public CommentDto(String comment) {
    }

    public static CommentDto fromEntity(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.id = entity.getId();
        dto.content = entity.getContent();
        return dto;
    }
}
