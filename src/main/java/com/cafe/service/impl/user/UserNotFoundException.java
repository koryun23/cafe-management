package com.cafe.service.impl.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("No user found having an id of " + id);
    }

    public UserNotFoundException(String username) {
        super("No user found having a username of " + username);
    }
}
