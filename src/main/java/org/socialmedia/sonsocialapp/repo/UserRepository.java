package org.socialmedia.sonsocialapp.repo;

import org.socialmedia.sonsocialapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u WHERE u.firstName LIKE %:query% OR u.email LIKE %:query%")
    List<User> searchUser(@Param("query") String query);
}
