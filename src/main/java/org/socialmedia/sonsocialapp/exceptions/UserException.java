package org.socialmedia.sonsocialapp.exceptions;


import org.socialmedia.sonsocialapp.model.User;

public class UserException extends Throwable {
    public UserException(String message){
        super(message);
    }
}
