package com.example.springprac2023.web;

import com.example.springprac2023.config.auth.LoginUser;
import com.example.springprac2023.config.auth.dto.SessionUser;
import com.example.springprac2023.service.PostsService;
import com.example.springprac2023.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) model.addAttribute("userName", user.getName());
        return "index";
    }
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
