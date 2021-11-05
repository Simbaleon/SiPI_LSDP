package com.example.insurance.Security;

public class SecurityConstants {
    public static final String SECRET = "My_Secret_Key";
    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 300000;
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 3600000;
    public static final String TOKEN_PREFIX = "Bearer_";
    public static final String HEADER_ACCESS_TOKEN = "AccessToken";
    public static final String HEADER_REFRESH_TOKEN = "RefreshToken";
}
