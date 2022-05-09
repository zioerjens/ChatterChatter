package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO implements Serializable {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public User toDomain() {
        User user = new User();
        user.setEmail(this.getEmail());
        user.setUsername(this.getUsername());
        user.setLastname(this.getLastName());
        user.setFirstname(this.getFirstName());
        return user;
    }
}
