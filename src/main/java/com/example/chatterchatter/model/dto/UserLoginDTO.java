package com.example.chatterchatter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO implements Serializable {

    @NotBlank(message = "validation:_The username can't be empty")
    @Size(max = 100, message = "validation:_The username is too long")
    private String username;

    @NotBlank(message = "validation:_The password can't be empty")
    @Size(max = 100, message = "validation:_The password is too long")
    private String password;
}
