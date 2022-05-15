package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @NotNull
    private Long id;

    @NotBlank(message = "The username can't be empty")
    @Size(max = 100, message = "The username is too long")
    private String username;

    @Size(max = 100, message = "The first name is too long")
    private String firstName;

    @Size(max = 100, message = "The last name is too long")
    private String lastName;

    @Email
    @NotBlank(message = "The email can't be empty")
    @Size(max = 100, message = "The email is too long")
    private String email;

    @NotNull
    private RoleEnum role;

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRole()
        );
    }

    public User toDomain() {
        User user = new User();
        user.setId(this.getId());
        user.setEmail(this.getEmail());
        user.setUsername(this.getUsername());
        user.setFirstname(this.getFirstName());
        user.setLastname(this.getLastName());
        user.setRole(this.role);
        return user;
    }
}
