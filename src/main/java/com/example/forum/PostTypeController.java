package com.example.forum;

import com.example.forum.entity.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post-types")
public class PostTypeController {
    private final PostTypeService postTypeService;
    private final ArticleService articleService;


}
