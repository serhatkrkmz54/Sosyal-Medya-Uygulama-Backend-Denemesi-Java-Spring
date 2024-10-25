package org.socialmedia.sonsocialapp.controller;

import org.socialmedia.sonsocialapp.service.PostService;
import org.socialmedia.sonsocialapp.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;
}
