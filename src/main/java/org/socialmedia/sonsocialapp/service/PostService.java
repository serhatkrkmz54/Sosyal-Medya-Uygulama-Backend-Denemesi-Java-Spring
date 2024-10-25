package org.socialmedia.sonsocialapp.service;

import org.socialmedia.sonsocialapp.exceptions.PostException;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.model.Post;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.request.PostReplyRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Post createPost(Post req, User user) throws UserException;
    Post findById(Long postId) throws PostException;
    public void deletePostById(Long postId, Long userId) throws PostException,UserException;
    Post createdReply(PostReplyRequest req, User user)throws PostException;
    List<Post> getUserPost(User user);
}
