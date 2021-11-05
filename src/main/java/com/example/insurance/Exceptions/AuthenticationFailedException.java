package com.example.insurance.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationFailedException extends ResponseStatusException {

    public AuthenticationFailedException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid email/password");
    }
}
