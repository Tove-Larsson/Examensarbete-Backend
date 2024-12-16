package com.tove.examensarbetebackend.exception;


public class RestaurantNameNotFoundException extends RuntimeException {

    public RestaurantNameNotFoundException(String message) {
        super(message);
    }
}
