package com.tove.examensarbetebackend.service;

import com.tove.examensarbetebackend.config.security.jwt.JWTService;
import com.tove.examensarbetebackend.model.dto.AppUserDTO;
import com.tove.examensarbetebackend.model.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO verify(AppUserDTO appUserDTO) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                appUserDTO.username(),
                                appUserDTO.password()
                        ));

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Role does not exist in user"))
                .substring(5);

        String generatedToken = jwtService.generateToken(
                authentication.getName()
        );
        return new AuthResponseDTO(
                generatedToken,
                role
        );
    }
}

