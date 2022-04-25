package com.example.chatterchatter.service.interfaces;

import com.example.chatterchatter.model.domain.User;

public interface AuthenticationServiceInterface {
    User register(String firstName, String lastName, String username, String email, String password) throws Exception;

    User login(String username, String password) throws Exception;
}
