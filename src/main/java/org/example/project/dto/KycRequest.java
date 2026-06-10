package org.example.project.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class KycRequest {
    private MultipartFile document;
}