package com.example.chatterchatter.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_USER", sequenceName ="SEQ_USER", initialValue = 100)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String firstname;
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    private Date lastLoginDate;

    private Date joinDate;

    private String password;

    private String role;

    @ElementCollection
    private List<String> authorities;

    private boolean isLocked;

    private boolean isActive;
}
