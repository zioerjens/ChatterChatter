package com.example.chatterchatter.model.domain;

import com.example.chatterchatter.model.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 100)
    private String username;

    @Size(max = 100)
    private String firstname;

    @Size(max = 100)
    private String lastname;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    private Date lastLoginDate;

    private Date joinDate;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(nullable = false)
    @NotNull
    private RoleEnum role;

    @Column(nullable = false)
    @NotEmpty
    @ElementCollection
    private List<String> authorities;

    @Column(nullable = false)
    @NotNull
    private boolean isLocked;

    @Column(nullable = false)
    @NotNull
    private boolean isActive;
}
