package org.socialmedia.sonsocialapp.dto;


import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private UserDTO user;
    private PostDTO post;
}
