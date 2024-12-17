package com.tove.examensarbetebackend.controller;

import com.tove.examensarbetebackend.model.dto.RestaurantDTO;
import com.tove.examensarbetebackend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {

        return restaurantService.createRestaurant(restaurantDTO);
    }


    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {

        return restaurantService.getAllRestaurants();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRestaurant(@RequestParam String name) {

        return restaurantService.deleteRestaurant(name);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<String> updateRestaurant(@PathVariable("name") String name, @RequestBody RestaurantDTO restaurantDTO) {

        return restaurantService.updateRestaurant(name, restaurantDTO);
    }

}
