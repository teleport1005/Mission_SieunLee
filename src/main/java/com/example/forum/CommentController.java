package com.example.forum;

import com.example.forum.dto.CommentDto;
import com.example.forum.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;
import java.util.List;


@Controller
@RequestMapping("article/read/{articleId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping
    public String showAllComment(
            @PathVariable("articleId")
            Long articleId,
            Model model)
        {
        List<Comment> comments = commentService.readCommentsByArticleId(articleId);
        model.addAttribute("comment", comments);
        return String.format("redirect:/article/read/%d", articleId);
        }


    @PostMapping
    public String write(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("content")
            String content,
            @RequestParam("password")
            String password
    ) {
        commentService.write(articleId, content, password);
        return String.format("redirect:/article/read/%d", articleId);
    }

    @GetMapping("{commentId}/edit")
    public String editCommentView(
            @PathVariable("commentId")
            Long commentId,
            @PathVariable("articleId")
            Long articleId,
            Model model
    ) {
        model.addAttribute("comment", commentService.readOneComment(commentId));
        model.addAttribute("article", articleService.readOneArticle(articleId));
        return "article/comment/edit";
    }
    @PostMapping("{commentId}/edit")
    public String editComment(
            @PathVariable("commentId") Long id,
            @PathVariable("articleId") Long articleId,
            @RequestParam("content") String content,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes
    ) {
        try {
            commentService.editComment(id, articleId, content, password);
            return String.format("redirect:/article/read/%d", articleId);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }

    @GetMapping("{commentId}/delete")
    public String delete(
            @PathVariable("commentId")
            Long commentId,
            @PathVariable("articleId")
            Long articleId,
            Model model
    ) {
        model.addAttribute("comment", commentService.readOneComment(commentId));
        model.addAttribute("article", articleService.readOneArticle(articleId));
        return "article/comment/delete";
    }



    @PostMapping("{commentId}/delete")
    public String delete(
            @PathVariable("commentId")
            Long commentId,
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("password")
            String password
    ) {
        commentService.deleteComment(commentId, password);
        return String.format("redirect:/article/read/%d", articleId);
    }

}
