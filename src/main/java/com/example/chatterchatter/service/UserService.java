package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import com.example.chatterchatter.repository.UserRepository;
import com.example.chatterchatter.service.interfaces.UserServiceInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User addUser(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) throws Exception {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if(existingUserOptional.isEmpty()){
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
}
