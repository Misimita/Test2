package org.example.project.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long id;
    private BigDecimal amount;
    private String type; // DEBIT / CREDIT
    private LocalDateTime createdAt;
}