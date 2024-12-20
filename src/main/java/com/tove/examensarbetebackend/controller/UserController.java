package com.tove.examensarbetebackend.controller;

import com.tove.examensarbetebackend.model.dto.AppUserDTO;
import com.tove.examensarbetebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDTO> registerUser(@Valid @RequestBody AppUserDTO appUserDTO) {

        return userService.createUser(appUserDTO);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<AppUserDTO> deleteUser(Authentication authentication) {
        return userService.deleteAuthenticatedUser(authentication);
    }

}
