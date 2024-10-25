package org.socialmedia.sonsocialapp.service;

import org.socialmedia.sonsocialapp.exceptions.PostException;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.model.Like;
import org.socialmedia.sonsocialapp.model.Post;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.repo.LikeRepository;
import org.socialmedia.sonsocialapp.repo.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikeRepository likeRepository, PostService postService, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @Override
    public Like likeTwit(Long postId, User user) throws UserException, PostException {
        Like islikeExist = likeRepository.isLikeExist(user.getId(), postId);

        if (islikeExist!=null) {
            likeRepository.deleteById(islikeExist.getId());
            return islikeExist;
        }
        Post post = postService.findById(postId);
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        Like savedLike = likeRepository.save(like);
        post.getLikes().add(savedLike);
        postRepository.save(post);

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long postId) throws PostException {
        Post post = postService.findById(postId);
        List<Like> likes = likeRepository.findByPostId(postId);
        return likes;
    }
}
