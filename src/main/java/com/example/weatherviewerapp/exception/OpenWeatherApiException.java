package com.example.weatherviewerapp.exception;

public class OpenWeatherApiException extends RuntimeException{
    public OpenWeatherApiException(String message) {
        super(message);
    }
}
