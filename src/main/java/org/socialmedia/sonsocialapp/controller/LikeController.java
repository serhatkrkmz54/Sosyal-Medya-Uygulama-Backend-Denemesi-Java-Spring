package org.socialmedia.sonsocialapp.controller;

import org.socialmedia.sonsocialapp.dto.LikeDTO;
import org.socialmedia.sonsocialapp.exceptions.PostException;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.mapper.LikeDTOMapper;
import org.socialmedia.sonsocialapp.model.Like;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.service.LikeService;
import org.socialmedia.sonsocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {

    private UserService userService;
    private LikeService likeService;

    public LikeController(UserService userService, LikeService likeService) {
        this.userService = userService;
        this.likeService = likeService;
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeDTO> likePosts(@PathVariable Long postId,
                                             @RequestHeader("Authorization") String jwt) throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Like like = likeService.likeTwit(postId, user);
        LikeDTO likeDTO = LikeDTOMapper.toLikeDTO(like,user);
        return new ResponseEntity<LikeDTO>(likeDTO, HttpStatus.CREATED);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<List<LikeDTO>> getAllLikes(@PathVariable Long postId,
                                                     @RequestHeader("Authorization") String jwt) throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Like> likes = likeService.getAllLikes(postId);
        List<LikeDTO> likeDTOS = LikeDTOMapper.toLikeDTOs(likes,user);
        return new ResponseEntity<>(likeDTOS,HttpStatus.CREATED);
    }


}
