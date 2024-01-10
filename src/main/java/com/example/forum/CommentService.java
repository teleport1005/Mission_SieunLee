package com.example.forum;

import com.example.forum.dto.CommentDto;
import com.example.forum.entity.Article;
import com.example.forum.entity.Comment;
import com.example.forum.repo.ArticleRepository;
import com.example.forum.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;



    public void write(
            Long articleId,
            String content,
            String password
    ) {
        Comment write = new Comment();
        write.setArticle(articleRepository.findById(articleId).orElse(null));
        write.setContent(content);
        write.setPassword(password);
        commentRepository.save(write);
    }

    public List<Comment> readCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public Comment readOneComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    public void editComment(
            Long id,
            Long articleId,
            String content,
            String password
    ) {
        Article article = articleRepository.findById(articleId).orElseThrow(()
                -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("댓글이 없습니다."));

        if (!comment.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        comment.setContent(content);
        commentRepository.save(comment);
    }

    public void deleteComment(Long id, String password) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        commentRepository.deleteById(id);
    }

}
