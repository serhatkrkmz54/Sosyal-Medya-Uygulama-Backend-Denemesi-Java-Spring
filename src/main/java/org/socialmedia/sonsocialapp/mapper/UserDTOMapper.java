package org.socialmedia.sonsocialapp.mapper;

import org.socialmedia.sonsocialapp.dto.UserDTO;
import org.socialmedia.sonsocialapp.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDTOMapper {

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setGender(user.getGender());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setBio(user.getBio());
        userDTO.setProfilePicture(user.getProfilePic());
        userDTO.setFollowers(Collections.singletonList(toUserDTO((User) user.getFollowers())));
        userDTO.setFollowing(Collections.singletonList(toUserDTO((User) user.getFollowings())));
        userDTO.setLogin_with_google(user.isLogin_with_google());
//        userDTO.setVerified(false);
        return userDTO;
    }

    public static List<UserDTO> toUserDTOs(List<User> followers) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : followers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setProfilePicture(user.getProfilePic());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }


}
