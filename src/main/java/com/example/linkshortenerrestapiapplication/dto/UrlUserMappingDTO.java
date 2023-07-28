package com.example.linkshortenerrestapiapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlUserMappingDTO {

    private String url;
    private String shortedUrl;
}
