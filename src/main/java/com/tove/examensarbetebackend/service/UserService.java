package com.tove.examensarbetebackend.service;

import com.tove.examensarbetebackend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tove.examensarbetebackend.model.AppUser;
import com.tove.examensarbetebackend.model.dto.AppUserDTO;

import static com.tove.examensarbetebackend.authorities.UserRole.ADMIN;
import static com.tove.examensarbetebackend.authorities.UserRole.USER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseEntity<AppUserDTO> createUser(AppUserDTO appUserDTO) {

        AppUser appUser = new AppUser(
                appUserDTO.username(),
                passwordEncoder.encode(appUserDTO.password()),
                USER,
                true,
                true,
                true,
                true
        );

        if (userRepository.findByUsername(appUserDTO.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userRepository.save(appUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(appUserDTO);
    }


    public ResponseEntity<AppUserDTO> createAdminUser(AppUserDTO appUserDTO) {

        if (userRepository.findByUsername(appUserDTO.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        AppUser newAdminUser = new AppUser(
                appUserDTO.username(),
                passwordEncoder.encode(appUserDTO.password()),
                ADMIN,
                true,
                true,
                true,
                true

        );

        userRepository.save(newAdminUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(appUserDTO);

    }
}
