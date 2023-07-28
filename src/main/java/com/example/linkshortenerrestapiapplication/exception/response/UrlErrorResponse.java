package com.example.linkshortenerrestapiapplication.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlErrorResponse {

    private String message;
    private long currentTime;
}
