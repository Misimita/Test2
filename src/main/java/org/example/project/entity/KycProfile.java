package org.example.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.project.entity.enums.KycStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KycProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String documentUrl;

    @Enumerated(EnumType.STRING)
    private KycStatus status = KycStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
}