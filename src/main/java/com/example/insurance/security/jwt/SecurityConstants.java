package com.example.insurance.security.jwt;

/**
 * The type Security constants.
 */
public class SecurityConstants {
    /**
     * The constant SECRET.
     */
    public static final String SECRET = "My_Secret_Key";
    /**
     * The constant ACCESS_TOKEN_EXPIRATION_TIME.
     */
    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 300000;
    /**
     * The constant REFRESH_TOKEN_EXPIRATION_TIME.
     */
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 3600000;
    /**
     * The constant TOKEN_PREFIX.
     */
    public static final String TOKEN_PREFIX = "Bearer_";
    /**
     * The constant HEADER_ACCESS_TOKEN.
     */
    public static final String HEADER_ACCESS_TOKEN = "AccessToken";
    /**
     * The constant HEADER_REFRESH_TOKEN.
     */
    public static final String HEADER_REFRESH_TOKEN = "RefreshToken";
}
