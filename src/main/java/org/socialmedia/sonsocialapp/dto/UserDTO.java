package org.socialmedia.sonsocialapp.dto;

import lombok.Data;
import org.socialmedia.sonsocialapp.enums.Gender;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
//    private String password;
    private String birthDate;
    private String phoneNumber;
    private String bio;
    private Gender gender;
    private String profilePicture;
    private boolean req_user;
    private List<UserDTO> followers = new ArrayList<>();
    private List<UserDTO> following = new ArrayList<>();
    private boolean followed;
    private boolean isVerified;
    private boolean login_with_google;

}
