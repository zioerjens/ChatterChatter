package com.example.chatterchatter.web.rest;

import com.example.chatterchatter.ChatterChatterApplication;
import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.dto.UserDTO;
import com.example.chatterchatter.model.dto.UserLoginDTO;
import com.example.chatterchatter.model.dto.UserRegisterDTO;
import com.example.chatterchatter.model.enums.RoleEnum;
import com.example.chatterchatter.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatterChatterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testLogin_withValidAdminCredentials_OK() {
        UserLoginDTO userLogin = new UserLoginDTO();
        userLogin.setUsername("admin");
        userLogin.setPassword("admin");

        HttpEntity<UserLoginDTO> entity = new HttpEntity<>(userLogin, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURLWithPort("/api/auth/login"),
                HttpMethod.POST, entity, UserDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("admin", response.getBody().getUsername());
        assertEquals(RoleEnum.ROLE_ADMIN, response.getBody().getRole());
        assertNotNull(response.getHeaders().get("Jwt-Token"));
    }

    @Test
    public void testLogin_withInvalidAdminPasswordAndValidUsername_NOK() {
        UserLoginDTO userLogin = new UserLoginDTO();
        userLogin.setUsername("admin");
        userLogin.setPassword("password");

        HttpEntity<UserLoginDTO> entity = new HttpEntity<>(userLogin, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURLWithPort("/api/auth/login"),
                HttpMethod.POST, entity, UserDTO.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getUsername());
    }

    @Test
    public void testLogin_withBlankUsername_NOK() {
        UserLoginDTO userLogin = new UserLoginDTO();
        userLogin.setUsername("");
        userLogin.setPassword("admin");

        HttpEntity<UserLoginDTO> entity = new HttpEntity<>(userLogin, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURLWithPort("/api/auth/login"),
                HttpMethod.POST, entity, UserDTO.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getUsername());
    }

    @Test
    public void testRegister_withValidFormData_OK() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("user");
        registerDTO.setEmail("user@user.ch");
        registerDTO.setFirstName("user");
        registerDTO.setLastName("user");
        registerDTO.setPassword("password");

        HttpEntity<UserRegisterDTO> entity = new HttpEntity<>(registerDTO, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURLWithPort("/api/auth/register"),
                HttpMethod.POST, entity, UserDTO.class);

        Optional<User> user = userRepository.findByUsername(registerDTO.getUsername());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("user", response.getBody().getUsername());
        assertTrue(user.isPresent());
        assertEquals(registerDTO.getUsername(), user.get().getUsername());
        assertEquals(RoleEnum.ROLE_USER, response.getBody().getRole());
    }

    @Test
    public void testRegister_withAlreadyRegisteredUser_OK() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("admin");
        registerDTO.setEmail("user@user.ch");
        registerDTO.setFirstName("user");
        registerDTO.setLastName("user");
        registerDTO.setPassword("password");

        HttpEntity<UserRegisterDTO> entity = new HttpEntity<>(registerDTO, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURLWithPort("/api/auth/register"),
                HttpMethod.POST, entity, UserDTO.class);

        Optional<User> user = userRepository.findByUsername(registerDTO.getUsername());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getUsername());
        assertTrue(user.isPresent());
        assertNotEquals(registerDTO.getEmail(), user.get().getEmail());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
