package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import com.example.chatterchatter.repository.MessageRepository;
import com.example.chatterchatter.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;

class UserServiceTest {

    UserRepository userRepo;
    BCryptPasswordEncoder passwordEncoder;
    MessageRepository messageRepository;
    UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        messageRepository = Mockito.mock(MessageRepository.class);
        userService = new UserService(userRepo, passwordEncoder, messageRepository);
    }

    @Test
    void findAllUsersExceptDeleted_returns_all_users_except_user_deleted() {
        var userList = new ArrayList<User>();
        userList.add(createUser(1L, "first"));
        userList.add(createUser(2L, "deleted"));
        userList.add(createUser(3L, "third"));
        when(userRepo.findAll()).thenReturn(userList);

        var result = this.userService.findAllUsersExceptDeleted();

        Assertions.assertTrue(result.stream().filter(u -> u.getUsername().equals("deleted")).findAny().isEmpty());
        Assertions.assertEquals(result.size(), 2L);
    }

    private User createUser(Long id, String username) {
        return new User(
                id,
                username,
                null,
                null,
                "test@test.ch",
                new Date(),
                new Date(),
                "password",
                RoleEnum.ROLE_USER,
                new ArrayList<>(),
                false,
                false
        );
    }
}
