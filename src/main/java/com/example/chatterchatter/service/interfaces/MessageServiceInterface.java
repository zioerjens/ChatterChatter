package com.example.chatterchatter.service.interfaces;

import com.example.chatterchatter.model.domain.ChatMessage;
import com.example.chatterchatter.model.dto.MessageDTO;

import java.util.List;

public interface MessageServiceInterface {

    List<ChatMessage> findAll();

    ChatMessage create(MessageDTO messageDTO);

    List<ChatMessage> findBySubjectId(Long subjectId);
}
