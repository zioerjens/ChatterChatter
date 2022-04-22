package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import com.example.chatterchatter.repository.UserRepository;
import com.example.chatterchatter.service.interfaces.AuthenticationServiceInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class AuthenticationService implements AuthenticationServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(String firstName, String lastName, String username, String email, String password) throws Exception {
        validateRegistration(username, email);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        String encodedPassword = encodePassword(password);
        user.setPassword(encodedPassword);
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setLocked(false);
        user.setRole(RoleEnum.ROLE_USER);
        user.setAuthorities(RoleEnum.ROLE_USER.getAuthorities());
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) throws Exception {
        User user = userService.findUserByUsername(username);
        if (user == null || user.getPassword() == null) {
            throw new AuthenticationCredentialsNotFoundException("User with given credentials not found.");
        }
        if (!verifyPassword(password, user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Password is not correct.");
        }
        authenticate(username, password);

        return user;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean verifyPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private void validateRegistration(String username, String email) throws Exception {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email)) {
            throw new Exception("Username or email is empty");
        }
        if (userService.findUserByUsername(username) != null) {
            throw new Exception("Username is already taken");
        }
        if (userService.findUserByEmail(email) != null) {
            throw new Exception("An user with this email is already registered");
        }
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
