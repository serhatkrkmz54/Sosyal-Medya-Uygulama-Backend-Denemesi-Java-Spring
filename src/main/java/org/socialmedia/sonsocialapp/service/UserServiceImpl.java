package org.socialmedia.sonsocialapp.service;

import org.socialmedia.sonsocialapp.config.JwtProvider;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public User findUserById(Long userId) throws UserException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserException("Bu id ile ilgili kullanıcı bulunamadı."));
        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null)  {
            throw new UserException("Bu email ile ilgili kullanıcı bulunamadı.");
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, User req) throws UserException {
        User user=findUserById(userId);
        if (req.getFirstName()!=null) {
            user.setFirstName(req.getFirstName());
        }
        if (req.getLastName()!=null){
            user.setLastName(req.getLastName());
        }
        if (req.getBio()!=null) {
            user.setLastName(req.getBio());
        }
        if (req.getBirthDate()!=null) {
            user.setBirthDate(req.getBirthDate());
        }
        if (req.getPhoneNumber()!=null) {
            user.setPhoneNumber(req.getPhoneNumber());
        }
        if (req.getProfilePic()!=null) {
            user.setProfilePic(req.getProfilePic());
        }
        if (req.getGender()!=null) {
            user.setGender(req.getGender());
        }
        return userRepository.save(user);
    }

    @Override
    public User followUser(Long userId, User user) throws UserException {

        User followToUser = findUserById(userId);
        if (user.getFollowings().contains(followToUser)&&followToUser.getFollowers().contains(user)) {
            user.getFollowings().remove(followToUser);
            followToUser.getFollowers().remove(user);
        }else {
            user.getFollowings().add(followToUser);
            followToUser.getFollowers().add(user);
        }
        userRepository.save(followToUser);
        userRepository.save(user);
        return followToUser;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
