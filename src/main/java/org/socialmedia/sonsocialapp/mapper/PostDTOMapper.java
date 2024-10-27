package org.socialmedia.sonsocialapp.mapper;

import org.socialmedia.sonsocialapp.dto.PostDTO;
import org.socialmedia.sonsocialapp.dto.UserDTO;
import org.socialmedia.sonsocialapp.model.Post;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.util.PostUtil;

import java.util.ArrayList;
import java.util.List;

public class PostDTOMapper {

    public static PostDTO toPostDTO(Post post, User reqUser) {
        UserDTO user = UserDTOMapper.toUserDTO(post.getUser());

        boolean isLiked = PostUtil.isLikedByReqUser(reqUser,post);

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setPostPicture(post.getPostPicture());
        postDTO.setTotalLikes(postDTO.getTotalLikes());
        postDTO.setTotalComment(post.getReplyPosts().size());
        postDTO.setUser(user);
        postDTO.setLiked(isLiked);
        postDTO.setReplyPosts(null);
        return postDTO;
    }

    public static List<PostDTO> toPostDTOs(List<Post> posts, User reqUser) {
        List<PostDTO> postDTOs = new ArrayList<>();
        for(Post post : posts) {
            PostDTO postDTO = toReplyPostDTO(post, reqUser);
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    private static PostDTO toReplyPostDTO(Post post, User reqUser) {
        return toPostDTO(post, reqUser);
    }


}
