package com.example.chatterchatter.service.interfaces;

import com.example.chatterchatter.model.domain.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> findAllUsers();

    User findUserById(Long userId);

    User addUser(User user);

    User updateUser(Long userId, User user) throws Exception;

    void deleteUser(Long userId);

    User findUserByUsername(String username) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
