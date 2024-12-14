package com.tove.examensarbetebackend.controller;

import com.tove.examensarbetebackend.model.dto.AppUserDTO;
import com.tove.examensarbetebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-admin")
    public ResponseEntity<AppUserDTO> createAdmin(@Valid @RequestBody AppUserDTO appUserDTO) {

        return userService.createAdminUser(appUserDTO);
    }
}
