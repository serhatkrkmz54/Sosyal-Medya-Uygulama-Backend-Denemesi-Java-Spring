package org.socialmedia.sonsocialapp.dto;

import lombok.Data;
import org.socialmedia.sonsocialapp.model.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private Long id;

    private String content;

    private String postPicture;

    private UserDTO user;

    private LocalDateTime createdAt;

    private int totalLikes;

    private int totalComment;

    private boolean isLiked;

    private List<PostDTO> replyPosts;
}
