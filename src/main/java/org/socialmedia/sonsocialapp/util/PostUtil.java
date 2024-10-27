package org.socialmedia.sonsocialapp.util;

import org.socialmedia.sonsocialapp.model.Like;
import org.socialmedia.sonsocialapp.model.Post;
import org.socialmedia.sonsocialapp.model.User;

public class PostUtil {
    public final static boolean isLikedByReqUser(User reqUser, Post post) {
        for (Like like: post.getLikes()) {
            if(like.getUser().getId().equals(reqUser.getId())){
                return true;
            }
        }
        return false;
    }
}
