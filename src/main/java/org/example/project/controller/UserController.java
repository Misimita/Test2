package org.example.project.controller;

import org.example.project.dto.UserResponseDto;
import org.example.project.entity.User;
import org.example.project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponseDto> users = userRepository.findAll(pageable)
                .map(u -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(u.getId());
                    dto.setUsername(u.getUsername());
                    dto.setEmail(u.getEmail());
                    dto.setFullName(u.getFullName());
                    dto.setPhone(u.getPhone());
                    dto.setKyc(u.isKyc());
                    return dto;
                });
        return ResponseEntity.ok(users);
    }
}