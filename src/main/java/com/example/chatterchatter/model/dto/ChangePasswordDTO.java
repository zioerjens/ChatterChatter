package com.example.chatterchatter.model.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
public class ChangePasswordDTO implements Serializable {

    @NotBlank(message = "validation:_Password cannot be empty")
    String password;

    @NotBlank(message = "validation:_Repeated password cannot be empty")
    String passwordRepeat;
}
