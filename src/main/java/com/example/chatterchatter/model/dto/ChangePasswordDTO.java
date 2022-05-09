package com.example.chatterchatter.model.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
public class ChangePasswordDTO implements Serializable {

    @NotBlank(message = "password cannot be empty")
    String password;

    @NotBlank(message = "passwordRepeat cannot be empty")
    String passwordRepeat;
}
