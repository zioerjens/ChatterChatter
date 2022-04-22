package com.example.chatterchatter.model.enums;

import com.example.chatterchatter.config.constant.AuthorityConstant;

import java.util.List;

public enum RoleEnum {
    ROLE_USER(AuthorityConstant.USER_AUTHORITIES),
    ROLE_ADMIN(AuthorityConstant.ADMIN_AUTHORITIES);

    private List<String> authorities;

    RoleEnum(List<String> authorities) {
        this.authorities = authorities;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
