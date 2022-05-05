package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import com.example.chatterchatter.repository.UserRepository;
import com.example.chatterchatter.service.interfaces.UserServiceInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User addUser(User users) throws Exception {
        validateRegistration(users.getUsername(), users.getEmail());
        User newUser = new User();
        newUser.setUsername(users.getUsername());
        newUser.setEmail(users.getEmail());
        newUser.setFirstname(users.getFirstname());
        newUser.setLastname(users.getLastname());
        newUser.setPassword(encodePassword(users.getPassword()));
        newUser.setJoinDate(new Date());
        newUser.setActive(true);
        newUser.setLocked(false);
        newUser.setRole(RoleEnum.ROLE_USER);
        newUser.setAuthorities(RoleEnum.ROLE_USER.getAuthorities());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long userId, User user) throws Exception {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isEmpty()) {
            throw new Exception("User not found.");
        }
        User existingUser = existingUserOptional.get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findUserByUsername(String username) throws Exception {
        if (StringUtils.isEmpty(username)) {
            throw new Exception("Username is empty");
        }
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        if (StringUtils.isEmpty(email)) {
            throw new Exception("Email is empty");
        }
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    private void validateRegistration(String username, String email) throws Exception {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email)) {
            throw new Exception("Username or email is empty");
        }
        if (findUserByUsername(username) != null) {
            throw new Exception("Username is already taken");
        }
        if (findUserByEmail(email) != null) {
            throw new Exception("An user with this email is already registered");
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
