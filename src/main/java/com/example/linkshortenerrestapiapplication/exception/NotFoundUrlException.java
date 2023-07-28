package com.example.linkshortenerrestapiapplication.exception;

public class NotFoundUrlException extends RuntimeException {
    public NotFoundUrlException(String message) {
        super(message);
    }
}
