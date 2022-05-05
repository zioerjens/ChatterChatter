package com.example.chatterchatter.web.rest;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.dto.UserDTO;
import com.example.chatterchatter.model.dto.UserRegisterDTO;
import com.example.chatterchatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception {
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
    public ResponseEntity<UserDTO> addUser(@RequestBody UserRegisterDTO userDTO) throws Exception {
        User user = convertRegisterUserDTOToDomain(userDTO);
        User savedUser = userService.addUser(user);
        UserDTO dto = convertUserToDTO(savedUser);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws Exception {
        User user = convertUserDTOToDomain(userDTO);
        User updatedUser = userService.updateUser(userId, user);
        UserDTO updatedUserDTO = convertUserToDTO(updatedUser);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) throws Exception {
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

    private User convertUserDTOToDomain(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setLastname(userDTO.getLastName());
        user.setFirstname(userDTO.getFirstName());
        return user;
    }

    private User convertRegisterUserDTOToDomain(UserRegisterDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setLastname(userDTO.getLastName());
        user.setFirstname(userDTO.getFirstName());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}
