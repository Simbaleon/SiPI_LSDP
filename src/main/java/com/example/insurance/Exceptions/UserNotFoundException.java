package com.example.insurance.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends ResponseStatusException {

    /**
     * Instantiates a new User not found exception.
     */
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "There is no user with such id");
    }
}
