package org.socialmedia.sonsocialapp.mapper;

import org.socialmedia.sonsocialapp.dto.LikeDTO;
import org.socialmedia.sonsocialapp.dto.PostDTO;
import org.socialmedia.sonsocialapp.dto.UserDTO;
import org.socialmedia.sonsocialapp.model.Like;
import org.socialmedia.sonsocialapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class LikeDTOMapper {

    public static LikeDTO toLikeDTO(Like like, User reqUser) {
        UserDTO userDTO = UserDTOMapper.toUserDTO(like.getUser());
        UserDTO reqUserDTO = UserDTOMapper.toUserDTO(reqUser);
        PostDTO postDTO = PostDTOMapper.toPostDTO(like.getPost(),reqUser);

        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setPost(postDTO);
        likeDTO.setUser(userDTO);
        return likeDTO;
    }

    public static List<LikeDTO> toLikeDTOs(List<Like> likes, User reqUser) {
        List<LikeDTO> likeDTOS = new ArrayList<>();
        for (Like like : likes) {
            UserDTO userDTO = UserDTOMapper.toUserDTO(like.getUser());
            PostDTO postDTO = PostDTOMapper.toPostDTO(like.getPost(),reqUser);
            LikeDTO likeDTO = new LikeDTO();
            likeDTO.setId(like.getId());
            likeDTO.setPost(postDTO);
            likeDTO.setUser(userDTO);
            likeDTOS.add(likeDTO);
        }
        return likeDTOS;
    }
}
