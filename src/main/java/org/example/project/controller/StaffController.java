package org.example.project.controller;

import org.example.project.entity.KycProfile;
import org.example.project.entity.enums.KycStatus;
import org.example.project.repository.KycProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    private final KycProfileRepository kycProfileRepository;

    public StaffController(KycProfileRepository kycProfileRepository) {
        this.kycProfileRepository = kycProfileRepository;
    }

    @PutMapping("/kyc/approve/{kycId}")
    public ResponseEntity<?> approveKyc(@PathVariable Long kycId) {
        KycProfile kyc = kycProfileRepository.findById(kycId).orElseThrow();
        kyc.setStatus(KycStatus.CONFIRM);
        // TODO: Cập nhật isKyc = true cho User
        kycProfileRepository.save(kyc);
        return ResponseEntity.ok("KYC approved");
    }
}