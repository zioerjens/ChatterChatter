package com.example.chatterchatter.config.constant;

import java.util.List;

public class AuthorityConstant {
    public static final List<String> USER_AUTHORITIES = List.of("user:read");
    public static final List<String> ADMIN_AUTHORITIES = List.of("user:read", "user:create", "user:update", "user:delete");
}
