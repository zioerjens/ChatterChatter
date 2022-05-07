package com.example.chatterchatter.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_MESSAGE", sequenceName = "SEQ_MESSAGE", initialValue = 100)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    User sender;

    @Column(nullable = false, unique = true)
    String content;

    @CreatedDate
    @Column(nullable = false)
    LocalDate time;

    @ManyToOne
    Subject subject;
}
