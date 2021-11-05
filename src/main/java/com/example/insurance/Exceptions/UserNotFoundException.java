package com.example.insurance.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "There is no user with such id");
    }
}
