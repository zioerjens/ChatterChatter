package com.example.chatterchatter.web.rest;

import com.example.chatterchatter.config.JwtTokenService;
import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.dto.UserDTO;
import com.example.chatterchatter.model.dto.UserLoginDTO;
import com.example.chatterchatter.model.dto.UserRegisterDTO;
import com.example.chatterchatter.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) throws Exception {
        User user = authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
        UserDTO dto = convertUserToDTO(user);
        HttpHeaders jwtHeader = jwtTokenService.getJwtHeader(user);
        return new ResponseEntity<>(dto, jwtHeader, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO userDTO) throws Exception {
        User user = authenticationService.register(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
        UserDTO dto = convertUserToDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private UserDTO convertUserToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setLastName(user.getLastname());
        dto.setFirstName(user.getFirstname());
        dto.setRole(user.getRole());
        return dto;
    }
}
