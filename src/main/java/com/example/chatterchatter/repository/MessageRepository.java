package com.example.chatterchatter.repository;

import com.example.chatterchatter.model.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllBySubjectId(Long subjectId);
}
