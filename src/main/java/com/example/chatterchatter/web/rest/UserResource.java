package com.example.chatterchatter.web.rest;

import com.example.chatterchatter.config.JwtTokenService;
import com.example.chatterchatter.model.UserPrincipal;
import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.service.UserService;
import com.example.chatterchatter.web.rest.dto.UserDTO;
import com.example.chatterchatter.web.rest.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.chatterchatter.config.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/api/user/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO loginDTO) throws Exception {
        authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        User user = userService.findUserByUsername(loginDTO.getUsername());
        UserPrincipal principal = new UserPrincipal(user);
        HttpHeaders jwtHeader = getJwtHeader(principal);
        UserDTO dto = convertUserToDTO(user);
        return new ResponseEntity<>(dto, jwtHeader, HttpStatus.OK);
    }

    @PostMapping("/api/user/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws Exception {
        User user = userService.register(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail());
        UserDTO dto = convertUserToDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal principal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenService.generateJwtToken(principal));
        return headers;
    }

    private UserDTO convertUserToDTO(User user) {
        if(user == null){
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setLastName(user.getLastname());
        dto.setFirstName(user.getFirstname());
        return dto;
    }
}
