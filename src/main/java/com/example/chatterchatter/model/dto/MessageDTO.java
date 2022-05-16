package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.ChatMessage;
import com.example.chatterchatter.model.domain.Subject;
import com.example.chatterchatter.model.domain.User;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Value
public class MessageDTO {

    Long id;

    Date time;

    UserDTO sender;

    @NotNull
    Long subjectId;

    @NotBlank(message = "validation:_The message content can't be empty")
    @Size(max = 250, message = "validation:_The message is too long")
    String content;

    public ChatMessage toDomain(User sender, Subject subject) {
        var localDateTime = getLocalDateTime();
        var message = new ChatMessage();
        message.setId(this.getId());
        message.setSender(sender);
        message.setTime(localDateTime);
        message.setSubject(subject);
        message.setContent(this.getContent());
        return message;
    }

    private LocalDateTime getLocalDateTime() {
        return isNotEmpty(this.getTime())
                ? this.getTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                : null;
    }

    public static MessageDTO from(ChatMessage message) {
        var date = dateFromChatMessage(message);
        return new MessageDTO(
                message.getId(),
                date,
                UserDTO.from(message.getSender()),
                message.getSubject().getId(),
                message.getContent()
        );
    }

    private static Date dateFromChatMessage(ChatMessage message) {
        return isNotEmpty(message.getTime())
                ? Date.from(message.getTime().atZone(ZoneId.systemDefault()).toInstant())
                : null;
    }

    public static List<MessageDTO> fromAll(List<ChatMessage> messages) {
        return messages.stream()
                .map(MessageDTO::from)
                .toList();
    }
}
