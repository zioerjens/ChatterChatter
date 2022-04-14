package com.example.chatterchatter.web.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class HelloWorldDTO implements Serializable {
    private String hello;
}
