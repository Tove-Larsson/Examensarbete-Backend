package com.tove.examensarbetebackend.controller;

import com.tove.examensarbetebackend.model.dto.AppUserDTO;
import com.tove.examensarbetebackend.model.dto.AuthResponseDTO;
import com.tove.examensarbetebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AppUserDTO appUserDTO) {

        return ResponseEntity.ok(authService.verify(appUserDTO));

    }

}
