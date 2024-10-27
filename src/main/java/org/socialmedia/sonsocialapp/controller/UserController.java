package org.socialmedia.sonsocialapp.controller;

import org.socialmedia.sonsocialapp.dto.UserDTO;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.mapper.UserDTOMapper;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.service.UserService;
import org.socialmedia.sonsocialapp.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        UserDTO userDTO = UserDTOMapper.toUserDTO(user);
        userDTO.setReq_user(true);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfileById(@PathVariable Long userId,
                                                      @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user1 = userService.findUserById(userId);

        UserDTO userDTO = UserDTOMapper.toUserDTO(user1);
        userDTO.setReq_user(UserUtil.isReqUser(reqUser, user1));
        userDTO.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user1));
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String query,
                                                    @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        List<User> user = userService.searchUser(query);
        List<UserDTO> userDTO = UserDTOMapper.toUserDTOs(user);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> searchUser(@RequestBody User req,
                                                    @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user = userService.updateUser(reqUser.getId(),req);
        UserDTO userDTO = UserDTOMapper.toUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }



}
