package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.ChatMessage;
import com.example.chatterchatter.model.domain.Subject;
import com.example.chatterchatter.model.domain.User;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class MessageDTO {
    Long id;
    LocalDate time;
    Long subjectId;
    String content;

    public ChatMessage toDomain(User sender, Subject subject) {
        var message = new ChatMessage();
        message.setId(this.getId());
        message.setSender(sender);
        message.setTime(this.getTime());
        message.setSubject(subject);
        message.setContent(this.getContent());
        return message;
    }

    public static MessageDTO from(ChatMessage message) {
        return new MessageDTO(
                message.getId(),
                message.getTime(),
                message.getSubject().getId(),
                message.getContent()
        );
    }

    public static List<MessageDTO> fromAll(List<ChatMessage> messages) {
        return messages.stream()
                .map(MessageDTO::from)
                .toList();
    }
}
