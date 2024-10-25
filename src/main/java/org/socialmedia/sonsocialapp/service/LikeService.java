package org.socialmedia.sonsocialapp.service;

import org.socialmedia.sonsocialapp.exceptions.PostException;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.model.Like;
import org.socialmedia.sonsocialapp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeService {
    Like likeTwit(Long postId, User user) throws UserException, PostException;
    List<Like> getAllLikes(Long postId) throws PostException;

}
