package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.ChatMessage;
import com.example.chatterchatter.model.domain.Subject;
import com.example.chatterchatter.model.domain.User;
import lombok.Value;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Value
public class MessageDTO {
    Long id;
    Date time;
    UserDTO sender;
    Long subjectId;
    String content;

    public ChatMessage toDomain(User sender, Subject subject) {
        var localDateTime = isNotEmpty(this.getTime())
                ? this.getTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                : null;
        var message = new ChatMessage();
        message.setId(this.getId());
        message.setSender(sender);
        message.setTime(localDateTime);
        message.setSubject(subject);
        message.setContent(this.getContent());
        return message;
    }

    public static MessageDTO from(ChatMessage message) {
        var date = isNotEmpty(message.getTime())
                ? Date.from(message.getTime().atZone(ZoneId.systemDefault()).toInstant())
                : null;
        return new MessageDTO(
                message.getId(),
                date,
                UserDTO.from(message.getSender()),
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
