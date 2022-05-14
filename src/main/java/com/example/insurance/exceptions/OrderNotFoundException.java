package com.example.insurance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Order not found exception.
 */
public class OrderNotFoundException extends ResponseStatusException {

    /**
     * Instantiates a new Order not found exception.
     */
    public OrderNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Заказа с таким id не найдено");
    }
}
