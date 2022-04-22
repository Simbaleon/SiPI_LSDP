package com.example.insurance.Data.Entities;

import org.springframework.security.core.GrantedAuthority;

/**
 * The enum Role.
 */
public enum Role implements GrantedAuthority {
    /**
     * User role.
     */
    USER,
    /**
     * Admin role.
     */
    ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
