package com.example.forum;

import com.example.forum.entity.Article;
import com.example.forum.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final PostTypeService postTypeService;

    @GetMapping
    public String readAll(Model model) {
        List<Board> boards = boardService.readAllBoard();
        List<Article> allArticles = new ArrayList<>();

        for (Board board : boards) {
            List<Article> articles = articleService.readArticlesByBoardId(board.getId());
            allArticles.addAll(articles);
        }

        // 모든 게시글을 역순으로 정렬
        Collections.reverse(allArticles);
        model.addAttribute("articles", allArticles);
        model.addAttribute("board", boardService.readAllBoard());
        model.addAttribute("postType", postTypeService.readAllPostType());
        return "home";
    }

    @GetMapping("/read/{id}")
    public String boardView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        List<Article> articles = articleService.readArticlesByBoardId(id);
        Collections.reverse(articles);
        model.addAttribute("articles", articles);
        model.addAttribute("board", boardService.readBoard(id));
        model.addAttribute("postType", postTypeService.readAllPostType());
        return "board/read";
    }

}
