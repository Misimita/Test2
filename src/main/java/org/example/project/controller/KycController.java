package org.example.project.controller;

import org.example.project.dto.KycRequest;
import org.example.project.entity.KycProfile;
import org.example.project.entity.User;
import org.example.project.repository.KycProfileRepository;
import org.example.project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/kyc")
public class KycController {

    private final UserRepository userRepository;
    private final KycProfileRepository kycProfileRepository;

    public KycController(UserRepository userRepository, KycProfileRepository kycProfileRepository) {
        this.userRepository = userRepository;
        this.kycProfileRepository = kycProfileRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadKyc(@ModelAttribute KycRequest request, @RequestParam Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Save file locally (temporary - no cloud)
        String fileName = UUID.randomUUID() + "_" + request.getDocument().getOriginalFilename();
        Path uploadPath = Paths.get("uploads/" + fileName);
        Files.createDirectories(uploadPath.getParent());
        Files.write(uploadPath, request.getDocument().getBytes());

        KycProfile kyc = new KycProfile();
        kyc.setUser(user);
        kyc.setDocumentUrl("/uploads/" + fileName);
        kyc.setStatus("PENDING");
        kycProfileRepository.save(kyc);

        return ResponseEntity.ok("KYC uploaded successfully. Status: PENDING");
    }
}