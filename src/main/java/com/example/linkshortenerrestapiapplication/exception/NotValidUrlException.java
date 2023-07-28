package com.example.linkshortenerrestapiapplication.exception;

public class NotValidUrlException extends RuntimeException {
    public NotValidUrlException(String message) {
        super(message);
    }
}
