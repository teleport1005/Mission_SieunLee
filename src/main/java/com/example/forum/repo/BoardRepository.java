package com.example.forum.repo;

import com.example.forum.entity.Article;
import com.example.forum.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Long countArticleById(Long id);


}
