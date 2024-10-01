package com.example.weatherviewerapp.exception;

public class InvalidStatusCodeException extends RuntimeException{
    public InvalidStatusCodeException(String message) {
        super(message);
    }
}
