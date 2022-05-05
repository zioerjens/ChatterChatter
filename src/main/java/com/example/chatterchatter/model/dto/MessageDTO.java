package com.example.chatterchatter.model.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class MessageDTO {
    Long id;
    String user;
    LocalDate date;
    String content;
}
