package com.example.forum.repo;

import com.example.forum.entity.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTypeRepository extends JpaRepository<PostType, Long> {

}
