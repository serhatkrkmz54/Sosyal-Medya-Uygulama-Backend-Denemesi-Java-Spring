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
    public Post findById(Long postId) throws PostException {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostException("Gönderi bulunamadı."));
        return post;
    }

    @Override
    public void deletePostById(Long postId, Long userId) throws PostException, UserException {
        Post post = findById(postId);
        if(!postId.equals(post.getUser().getId())){
            throw new UserException("Başka kullanıcının gönderilerini silemezsiniz.");
        }
        postRepository.deleteById(post.getId());
    }

    @Override
    public Post createdReply(PostReplyRequest req, User user) throws PostException {
        Post replyFor = findById(req.getPostId());
        Post post = new Post();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setPostPicture(req.getPostPicture());
        post.setReply(true);
        post.setReplyFor(replyFor);
        Post savedPost = postRepository.save(post);
        post.getReplyPosts().add(savedPost);
        postRepository.save(replyFor);
        return replyFor;
    }

    @Override
    public List<Post> getUserPost(User user) {
        return postRepository.userIdyeGorePostBul(user.getId());
    }

}
