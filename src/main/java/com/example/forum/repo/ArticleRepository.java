package com.example.forum.repo;

import com.example.forum.entity.Article;
import com.example.forum.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {


    List<Article> findArticleByBoardId(Long boardId);

}
