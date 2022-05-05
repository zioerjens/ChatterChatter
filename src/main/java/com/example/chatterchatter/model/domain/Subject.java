package com.example.chatterchatter.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER", initialValue = 100)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    String title;

}