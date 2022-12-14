package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import com.example.chatterchatter.repository.MessageRepository;
import com.example.chatterchatter.repository.UserRepository;
import com.example.chatterchatter.service.interfaces.UserServiceInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageRepository messageRepository;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageRepository = messageRepository;
    }

    @Override
    public List<User> findAllUsersExceptDeleted() {
        return userRepository.findAll().stream().filter(u -> !u.getUsername().equals("deleted")).toList();
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User addUser(User user) throws Exception {
        validateRegistration(user.getUsername(), user.getEmail());
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setPassword(encodePassword(user.getPassword()));
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
        var deletedUser = userRepository.findByUsername("deleted").orElseThrow(
                () -> new IllegalStateException("Deleted User could not be found.")
        );
        var messagesFromUserToBeDeleted = messageRepository.findAllBySenderId(userId);
        messagesFromUserToBeDeleted.forEach(m -> m.setSender(deletedUser));
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

    public User increasePrivileges(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        user.setRole(RoleEnum.ROLE_ADMIN);
        user.getAuthorities().addAll(RoleEnum.ROLE_ADMIN.getAuthorities().stream().filter(a -> !user.getAuthorities().contains(a)).toList());
        return userRepository.save(user);
    }

    public User changePassword(Long userId, String password, String passwordRepeat) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        if (passwordRepeat == null || !passwordRepeat.equals(password)) {
            throw new Exception("Delivered password is invalid");
        }
        user.setPassword(encodePassword(password));
        return userRepository.save(user);
    }
}
