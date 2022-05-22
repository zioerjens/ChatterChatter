package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO implements Serializable {

    @NotBlank(message = "validation:_The username can't be empty")
    @Size(max = 100, message = "validation:_The username is too long")
    private String username;

    @Email(message = "validation:_Not a valid email")
    @NotBlank(message = "validation:_The email can't be empty")
    @Size(max = 100, message = "validation:_The email is too long")
    private String email;

    @Size(max = 100, message = "validation:_The first name is too long")
    private String firstName;

    @Size(max = 100, message = "validation:_The last name is too long")
    private String lastName;

    @NotBlank(message = "validation:_The password can't be empty")
    @Size(max = 100, message = "validation:_The password is too long")
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
