package com.example.insurance.exceptions;

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
        super(HttpStatus.NOT_FOUND, "Пользователя с таким id нет");
    }
}
