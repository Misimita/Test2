package org.example.project.controller;

import org.example.project.dto.TransactionDto;
import org.example.project.entity.Transaction;
import org.example.project.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionHistoryController {

    private final TransactionRepository transactionRepository;

    public TransactionHistoryController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/history")
    public ResponseEntity<Page<TransactionDto>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Pageable pageable = PageRequest.of(page, size);
        // Tạm thời dùng accountId = 1, sau sẽ lấy từ UserDetails
        Page<Transaction> txPage = transactionRepository.findByFromAccountIdOrToAccountId(1L, 1L, pageable);

        Page<TransactionDto> dtoPage = txPage.map(tx -> {
            TransactionDto dto = new TransactionDto();
            dto.setId(tx.getId());
            dto.setAmount(tx.getAmount());
            dto.setCreatedAt(tx.getCreatedAt());
            // Xác định DEBIT / CREDIT
            // Logic tạm thời
            return dto;
        });

        return ResponseEntity.ok(dtoPage);
    }
}