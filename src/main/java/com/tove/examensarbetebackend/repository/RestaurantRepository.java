package com.tove.examensarbetebackend.repository;

import com.tove.examensarbetebackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String name);
    Optional<Restaurant> findByNameIgnoreCase(String name);

}
