package com.tove.examensarbetebackend.controller;

import com.tove.examensarbetebackend.model.dto.RestaurantDTO;
import com.tove.examensarbetebackend.service.RestaurantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;

    @Test
    @DisplayName("Test to get all restaurants and if the returned value is correct and list size")
    void testGetAllRestaurants() throws Exception {
        List<RestaurantDTO> mockRestaurants = List.of(
                new RestaurantDTO("Name1", "Address1", "City1", 1),
                new RestaurantDTO("Name2", "Address2", "City2", 4)
        );

        Mockito.when(restaurantService.getAllRestaurants())
                .thenReturn(ResponseEntity.ok(mockRestaurants));

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurant/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Name1"));
    }



}