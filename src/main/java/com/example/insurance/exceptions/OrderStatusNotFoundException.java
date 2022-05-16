package com.example.insurance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Order status not found exception.
 */
public class OrderStatusNotFoundException extends ResponseStatusException {

    /**
     * Instantiates a new Order status not found exception.
     */
    public OrderStatusNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Не найден статус");
    }

}
