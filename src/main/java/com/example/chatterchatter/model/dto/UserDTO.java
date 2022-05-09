package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private RoleEnum role;

    public User toDomain() {
        User user = new User();
        user.setId(this.getId());
        user.setEmail(this.getEmail());
        user.setUsername(this.getUsername());
        user.setLastname(this.getLastName());
        user.setFirstname(this.getFirstName());
        user.setRole(this.role);
        return user;
    }
}
