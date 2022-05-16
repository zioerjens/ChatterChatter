package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.ChatMessage;
import com.example.chatterchatter.model.domain.Subject;
import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import com.example.chatterchatter.repository.MessageRepository;
import com.example.chatterchatter.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void deleteUser_changes_message_sender_to_deleted() {
        var deletedUser = createUser(1L, "deleted");
        var userToDelete = createUser(2L, "userToDelete");
        when(userRepo.findByUsername("deleted")).thenReturn(Optional.of(deletedUser));
        var messageList = new ArrayList<ChatMessage>();
        messageList.add(createMessage(1L, userToDelete));
        when(messageRepository.findAllBySenderId(userToDelete.getId())).thenReturn(messageList);

        this.userService.deleteUser(userToDelete.getId());

        Assertions.assertTrue(messageList.stream().allMatch(m -> m.getSender().getUsername().equals("deleted")));
    }

    @Test
    void updateUser_throws_if_user_does_not_exist() {
        when(userRepo.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(
                Exception.class,
                () -> this.userService.updateUser(1L, createUser(1L, "name")),
                "User not found."
        );
    }

    @Test
    void findUserByEmail_throws_if_email_is_empty() {
        Assertions.assertThrowsExactly(
                Exception.class,
                () -> this.userService.findUserByEmail(""),
                "Email is empty"
        );
    }

    @Test
    void findUserByUsername_throws_if_username_is_empty() {
        Assertions.assertThrowsExactly(
                Exception.class,
                () -> this.userService.findUserByUsername(""),
                "Username is empty"
        );
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

    private ChatMessage createMessage(Long id, User sender) {
        return new ChatMessage(
                id,
                sender,
                "test",
                LocalDateTime.now(),
                new Subject()
        );
    }
}
