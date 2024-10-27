package org.socialmedia.sonsocialapp.controller;

import org.socialmedia.sonsocialapp.dto.PostDTO;
import org.socialmedia.sonsocialapp.exceptions.PostException;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.mapper.PostDTOMapper;
import org.socialmedia.sonsocialapp.model.Post;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.request.PostReplyRequest;
import org.socialmedia.sonsocialapp.response.ApiResponse;
import org.socialmedia.sonsocialapp.service.PostService;
import org.socialmedia.sonsocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/post-olustur")
    public ResponseEntity<PostDTO> createPost(@RequestBody Post req,
                                               @RequestHeader("Authorization") String jwt) throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createPost(req,user);
        PostDTO postDTO = PostDTOMapper.toPostDTO(post,user);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PostMapping("/cevapla")
    public ResponseEntity<PostDTO> replPost(@RequestBody PostReplyRequest req,
                                            @RequestHeader("Authorization") String jwt) throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createdReply(req,user);
        PostDTO postDTO = PostDTOMapper.toPostDTO(post,user);
        return new ResponseEntity<>(postDTO,HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> findPostById(@PathVariable Long postId,
                                                @RequestHeader("Authorization") String jwt) throws UserException,PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.findById(postId);
        PostDTO postDTO = PostDTOMapper.toPostDTO(post,user);
        return new ResponseEntity<>(postDTO,HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId,
                                              @RequestHeader("Authorization") String jwt) throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        postService.deletePostById(postId,user.getId());
        ApiResponse response = new ApiResponse();
        response.setMessage("Gönderi başarıyla silindi.");
        response.setStatus(true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    @GetMapping("/")
//    public ResponseEntity<List<PostDTO>> getAllPosts(@RequestHeader("Authorization") String jwt) throws UserException,PostException {
//        User user = userService.findUserProfileByJwt(jwt);
//        List<Post> posts = postService.getAllPosts();
//        List<PostDTO> postDTOS = PostDTOMapper.toPostDTOs(posts,user);
//        return new ResponseEntity<>(postDTOS,HttpStatus.OK);
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getAllUsersPost(@PathVariable Long userId,
                                                         @RequestHeader("Authorization") String jwt) throws UserException,PostException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Post> posts = postService.getUserPost(user);
        List<PostDTO> postDTOS = PostDTOMapper.toPostDTOs(posts,user);
        return new ResponseEntity<>(postDTOS,HttpStatus.OK);
    }

}
