package com.example.forum.repo;

import com.example.forum.entity.Article;
import com.example.forum.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {


    List<Article> findArticleByBoardId (Long boardId);

    List<Article> findByBoardIdOrderByIdDesc(Long boardId);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% OR a.contents LIKE %:keyword%")
    List<Article> findByTitleOrContents(@Param("keyword") String keyword);

}
