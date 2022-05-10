package com.example.chatterchatter.service.interfaces;

import com.example.chatterchatter.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<User> findAllUsers();

    Optional<User> findUserById(Long userId);

    User addUser(User user) throws Exception;

    User updateUser(Long userId, User user) throws Exception;

    void deleteUser(Long userId);

    User findUserByUsername(String username) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
