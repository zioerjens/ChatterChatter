package com.example.chatterchatter.model.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class ChangePasswordDTO implements Serializable {
    String password;
    String passwordRepeat;
}
