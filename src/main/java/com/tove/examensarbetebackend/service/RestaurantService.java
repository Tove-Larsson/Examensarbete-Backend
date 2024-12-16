package com.tove.examensarbetebackend.service;

import com.tove.examensarbetebackend.exception.RestaurantNameNotFoundException;
import com.tove.examensarbetebackend.model.Restaurant;
import com.tove.examensarbetebackend.model.dto.RestaurantDTO;
import com.tove.examensarbetebackend.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<RestaurantDTO> restaurantDTOs = restaurants.stream()
                .map(restaurant -> new RestaurantDTO(
                        restaurant.getName(),
                        restaurant.getAddress(),
                        restaurant.getCity(),
                        restaurant.getToilet()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(restaurantDTOs);
    }

    public ResponseEntity<RestaurantDTO> createRestaurant(RestaurantDTO restaurantDTO) {

        Restaurant newRestaurant = new Restaurant(
                restaurantDTO.name(),
                restaurantDTO.address(),
                restaurantDTO.city(),
                restaurantDTO.toilet()
        );

        restaurantRepository.save(newRestaurant);

        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantDTO);
    }

    public ResponseEntity<String> deleteRestaurant(String name) {

        Optional<Restaurant> restaurantToDelete = restaurantRepository.findByName(name);

        if (restaurantToDelete.isEmpty()) {
            System.out.println("In If statement in delete " + name);
            throw new RestaurantNameNotFoundException(name + " Could not be found");
        }

        Restaurant restaurant = restaurantToDelete.get();
        restaurantRepository.delete(restaurant);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(restaurant.getName());
    }
}
