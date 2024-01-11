package com.example.forum;

import com.example.forum.dto.ArticleDto;
import com.example.forum.entity.Article;
import com.example.forum.entity.Board;
import com.example.forum.entity.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("article")
public class ArticleController {
    private final ArticleService articleService;
    private final BoardService boardService;
    private final PostTypeService postTypeService;


    @GetMapping("write-view/{boardId}")
    public String writeView(
            @PathVariable("boardId")
            Long boardId,
            Model model
    ) {

        Board board = boardService.readBoard(boardId);
        List<PostType> postTypes = postTypeService.readAllPostType();

        ArticleDto articleDto = new ArticleDto();
        articleDto.setBoardId(boardId);

        model.addAttribute("board", board);
        model.addAttribute("postTypes", postTypes);
        model.addAttribute("boardId", articleDto);
        return "article/write";
    }


    @PostMapping("write/{boardId}")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam("password") String password,
            @RequestParam("postType") Long postTypeId,
            @PathVariable("boardId") Long boardId
    ) {
        articleService.write(title, contents, password, postTypeId, boardId);
        return "redirect:/board/read/" + boardId;
    }



    @GetMapping("/home")
    public String readAll(Model model) {
        List<Article> articles = articleService.readAllArticle();
        Collections.reverse(articles); // 역순으로 보여져야 함


        Long articleCount = articleService.getTotalArticleCount();
        model.addAttribute("articleCount", articleCount);

        List<Board> boards = boardService.readAllBoard();
        model.addAttribute("boards", boards);
        model.addAttribute("article", articles);

        List<PostType> postTypes = postTypeService.readAllPostType();
        model.addAttribute("postTypes", postTypes);

        return "home";
    }

    @GetMapping("/read/{id}")
    public String readOne(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        Article article = articleService.readOneArticle(id);
        model.addAttribute("article", article);
        return "article/read";
    }

    @GetMapping("{id}/edit")
    public String editArticleView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.readOneArticle(id));
        model.addAttribute("board", boardService.readAllBoard());
        model.addAttribute("postType", postTypeService.readAllPostType());
        return "article/edit";
    }
    @PostMapping("{id}/edit")
    public String editArticle(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam String password,
            @RequestParam("post-id") Long postId,
            @RequestParam("board-id") Long boardId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            articleService.editArticle(id, title, contents, password, postId, boardId);
            return "redirect:/article/home";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }

    @GetMapping("{id}/delete")
    public String deleteArticleView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.readOneArticle(id));
        model.addAttribute("postType", postTypeService.readAllPostType());
        model.addAttribute("board", boardService.readAllBoard());
        return "article/delete";
    }

    @PostMapping("{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id, @RequestParam String password) {
        articleService.deleteArticle(id, password);
        return "redirect:/article/home";
    }

    @GetMapping("search")
    public String findByTitleOrContents(
            @RequestParam(value = "keyword")
            String keyword,
            Model model
    ) {
        try {
            List<Article> articlesPage = articleService.findByTitleOrContents(keyword);
            model.addAttribute("articleList", articlesPage);
            return "article/search";
        } catch (IllegalArgumentException e) {
            return "article/search";
        } catch (Exception e) {
            return "error";
        }
    }





}
