package com.example.chatterchatter.config.constant;

public class SecurityConstant {
    public static final Long EXPIRATION_TIME = 432_000_000L; // 5 days expiration-time in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String WEBEC_ISSUER = "Zioerjen,Zimmermann, LLC";
    public static final String WEBEC_ADMINISTRATION = "Chatter-Chatter";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/api/auth/login", "/api/auth/register"};

}
