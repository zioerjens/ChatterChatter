package com.example.chatterchatter.service.interfaces;

import com.example.chatterchatter.model.domain.User;

import java.util.List;

public interface UserServiceInterface {
    User register(String firstName, String lastName, String username, String email) throws Exception;
    List<User> findAllUsers();
    User findUserByUsername(String username) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
