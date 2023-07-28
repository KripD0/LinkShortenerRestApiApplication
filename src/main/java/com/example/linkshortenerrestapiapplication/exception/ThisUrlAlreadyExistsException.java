package com.example.linkshortenerrestapiapplication.exception;

public class ThisUrlAlreadyExistsException extends RuntimeException {

    public ThisUrlAlreadyExistsException(String message) {
        super(message);
    }
}
