package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.ChatMessage;
import com.example.chatterchatter.model.dto.MessageDTO;
import com.example.chatterchatter.repository.MessageRepository;
import com.example.chatterchatter.repository.SubjectRepository;
import com.example.chatterchatter.repository.UserRepository;
import com.example.chatterchatter.service.interfaces.MessageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageService implements MessageServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public ChatMessage create(MessageDTO messageDTO) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        var user = userRepository.findByUsername(userName);
        var subject = subjectRepository.findById(messageDTO.getSubjectId());
        var message = messageDTO.toDomain(
                user.orElseThrow(() -> new IllegalStateException("User not found")),
                subject.orElseThrow(() -> new IllegalStateException("Subject not found"))
        );
        return messageRepository.save(message);
    }

    @Override
    public List<ChatMessage> findAll() {
        return messageRepository.findAll();
    }
}
