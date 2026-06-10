package org.example.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String fullName;
    private String phone;

    private boolean isKyc = false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    private LocalDateTime createdAt = LocalDateTime.now();
}

enum Role {
    ADMIN, STAFF, CUSTOMER
}