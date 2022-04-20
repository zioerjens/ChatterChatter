package com.example.chatterchatter.service;

import com.example.chatterchatter.model.UserPrincipal;
import com.example.chatterchatter.model.domain.RoleEnum;
import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.repository.UserRepository;
import com.example.chatterchatter.service.interfaces.UserServiceInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserServiceInterface, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found by username: "+ username);
        }
        User user = userOptional.get();
        user.setLastLoginDate(new Date());
        userRepository.save(user);

        return new UserPrincipal(user);
    }

    @Override
    public User register(String firstName, String lastName, String username, String email) throws Exception {
        validateRegistration(username, email);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        String encodedPassword = encodePassword("password");
        user.setPassword(encodedPassword);
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setLocked(false);
        user.setRole(RoleEnum.ROLE_USER.name());
        user.setAuthorities(RoleEnum.ROLE_USER.getAuthorities());
        return userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateRegistration(String username, String email) throws Exception {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(email)){
            throw new Exception("Username or email is empty");
        }
        if(findUserByUsername(username) != null){
            throw new Exception("Username is already taken");
        }
        if(findUserByEmail(email) != null){
            throw new Exception("An user with this email is already registered");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) throws Exception {
        if(StringUtils.isEmpty(username)){
            throw new Exception("Username is empty");
        }
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        if(StringUtils.isEmpty(email)){
            throw new Exception("Email is empty");
        }
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
}
