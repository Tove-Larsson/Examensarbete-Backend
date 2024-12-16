package com.tove.examensarbetebackend.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RestaurantDTO (

        @NotBlank(message = "Name can not be blank")
        String name,
        @NotBlank(message = "Address can not be blank")
        String address,
        String city,
        int toilet
){


}
