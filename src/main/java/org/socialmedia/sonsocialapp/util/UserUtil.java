package org.socialmedia.sonsocialapp.util;

import org.socialmedia.sonsocialapp.model.User;

public class UserUtil {

    public static boolean isReqUser(User reqUser, User user2) {
        return reqUser.getId().equals(user2.getId());
    }

    public static boolean isFollowedByReqUser(User reqUser, User user2) {
        return reqUser.getFollowings().contains(user2);
    }
}
