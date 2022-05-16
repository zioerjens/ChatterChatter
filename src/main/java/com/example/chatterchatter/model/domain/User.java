package com.example.chatterchatter.model.domain;

import com.example.chatterchatter.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "validation:_Username can't be empty")
    private String username;

    private String firstname;

    private String lastname;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;

    private Date lastLoginDate;

    private Date joinDate;

    @Column(nullable = false)
    @NotBlank(message = "validation:_Hashed password can't be empty")
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
