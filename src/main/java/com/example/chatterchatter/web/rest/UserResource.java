package com.example.chatterchatter.web.rest;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.dto.UserDTO;
import com.example.chatterchatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers().stream().map(this::convertUserToDTO).toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);
        UserDTO userDTO = convertUserToDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalStateException("UserResource.addUser() - request body was empty");
        }
        User user = userDTO.toDomain();
        User savedUser = userService.addUser(user);
        UserDTO dto = convertUserToDTO(savedUser);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws Exception {
        if (userDTO == null) {
            throw new IllegalStateException("UserResource.updateUser() - request body was empty");
        }
        User user = userDTO.toDomain();
        User updatedUser = userService.updateUser(userId, user);
        UserDTO updatedUserDTO = convertUserToDTO(updatedUser);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private UserDTO convertUserToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setLastName(user.getLastname());
        dto.setFirstName(user.getFirstname());
        return dto;
    }
}
