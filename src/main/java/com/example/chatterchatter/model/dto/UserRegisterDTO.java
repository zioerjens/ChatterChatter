package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO implements Serializable {

    @NotBlank(message = "The username can't be empty")
    private String username;

    @Email
    @NotBlank(message = "The email can't be empty")
    private String email;

    private String firstName;

    private String lastName;

    @NotBlank(message = "The password can't be empty")
    private String password;

    public User toDomain() {
        User user = new User();
        user.setEmail(this.getEmail());
        user.setUsername(this.getUsername());
        user.setLastname(this.getLastName());
        user.setFirstname(this.getFirstName());
        user.setPassword(this.getPassword());
        return user;
    }
}
