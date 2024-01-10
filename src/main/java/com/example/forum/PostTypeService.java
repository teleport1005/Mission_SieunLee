package com.example.forum;

import com.example.forum.entity.PostType;
import com.example.forum.repo.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostTypeService {
    private final PostTypeRepository postTypeRepository;

    public List<PostType> readAllPostType() {
        return postTypeRepository.findAll();
    }

    public PostType selectPostType(Long id) {
        return postTypeRepository.findById(id).orElse(null);
    }
}
