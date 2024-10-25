package org.socialmedia.sonsocialapp.repo;

import org.socialmedia.sonsocialapp.model.Post;
import org.socialmedia.sonsocialapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT P FROM Post P JOIN P.likes I where I.user.id=:userId")
    List<Post> userIdyeGorePostBul(Long userId);

}
