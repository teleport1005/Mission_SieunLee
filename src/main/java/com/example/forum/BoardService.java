package com.example.forum;

import com.example.forum.entity.Board;
import com.example.forum.repo.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;


    public Board readBoard(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public List<Board> readAllBoard() {
        return boardRepository.findAll();
    }

}
