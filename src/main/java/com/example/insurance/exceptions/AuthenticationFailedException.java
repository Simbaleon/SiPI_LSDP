package com.example.insurance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Authentication failed exception.
 */
public class AuthenticationFailedException extends ResponseStatusException {

    /**
     * Instantiates a new Authentication failed exception.
     */
    public AuthenticationFailedException() {
        super(HttpStatus.UNAUTHORIZED, "Неверный адрес эл.почты или пароль");
    }
}
