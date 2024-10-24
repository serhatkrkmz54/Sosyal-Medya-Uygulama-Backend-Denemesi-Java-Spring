package org.socialmedia.sonsocialapp.service;

import org.socialmedia.sonsocialapp.exceptions.PostException;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.model.Post;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.repo.PostRepository;
import org.socialmedia.sonsocialapp.request.PostReplyRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post req, User user) throws UserException {

        Post post = new Post();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setPostPicture(req.getPostPicture());
        post.setUser(user);
        post.setReply(false);
        post.setPost(true);

        return postRepository.save(post);
    }

    @Override
    public List<Post> findAllPost() {
        return List.of();
    }

    @Override
    public Post findById(Long postId) throws PostException {
        return null;
    }

    @Override
    public void deletePostById(Long postId, Long userId) throws PostException, UserException {

    }

    @Override
    public Post createdReply(PostReplyRequest req, User user) throws PostException {
        return null;
    }

    @Override
    public List<Post> getUserPost(User user) {
        return List.of();
    }

    @Override
    public List<Post> begeniyeGoreKullanici(User user) {
        return List.of();
    }
}
