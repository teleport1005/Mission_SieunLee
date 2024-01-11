package com.example.forum;

import com.example.forum.dto.ArticleDto;
import com.example.forum.entity.Article;
import com.example.forum.entity.Board;
import com.example.forum.entity.PostType;
import com.example.forum.repo.ArticleRepository;
import com.example.forum.repo.BoardRepository;
import com.example.forum.repo.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final PostTypeRepository postTypeRepository;




    public void write(
            String title,
            String contents,
            String password,
            Long postId,
            Long boardId
    ) {
        Article article = new Article();
        article.setTitle(title);
        article.setContents(contents);
        article.setPassword(password);
        article.setPostType(postTypeRepository.findById(postId).orElse(null));
        article.setBoard(boardRepository.findById(boardId).orElse(null));

        articleRepository.save(article);
    }

    public void editArticle(
            Long id, String title, String contents, String password, Long boardId, Long postId
    ) {
        Article article = articleRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(()
                -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
        PostType postType = postTypeRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("말머리가 입력되지 않았습니다."));

        if (!article.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        article.setTitle(title);
        article.setContents(contents);
        article.setBoard(board);
        article.setPostType(postType);
        articleRepository.save(article);
    }

    public void deleteArticle(Long id, String password) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (!article.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        articleRepository.deleteById(id);
    }


    public Article readOneArticle(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.orElse(null);
    }

    // 전체글 표시
    public List<Article> readAllArticle() {
        return articleRepository.findAll();
    }

    // 게시판별 게시글 표시
    public List<Article> readArticlesByBoardId(Long boardId) {
        return articleRepository.findArticleByBoardId(boardId);
    }
    public List<Article> readArticlesByBoardIdAtHome(Long boardId) {
        return articleRepository.findByBoardIdOrderByIdDesc(boardId);
    }


    // 전체글 개수 표시
    public Long getTotalArticleCount() {
        return articleRepository.count();
    }

    // 글 제목으로 검색하는 기능
    public List<Article> findByTitleOrContents(String keyword) {
        try {
            return articleRepository.findByTitleOrContents(keyword);
        } catch (Exception e) {
            throw new IllegalArgumentException("검색 결과가 없습니다", e);
        }
    }

}

