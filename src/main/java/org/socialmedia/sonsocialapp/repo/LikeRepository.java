package org.socialmedia.sonsocialapp.repo;

import org.socialmedia.sonsocialapp.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT I FROM Like I WHERE I.user.id=:userId AND I.post.id=:postId")
    Like isLikeExist(@Param("userId") Long userId,@Param("postId") Long postId);

    @Query("SELECT I FROM Like I WHERE I.post.id=:postId")
    List<Like> findByPostId(@Param("postId")Long postId);


}
