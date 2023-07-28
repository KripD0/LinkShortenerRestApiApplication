package com.example.linkshortenerrestapiapplication.service;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.model.UrlMapping;

public interface UrlMappingService {

    UrlMapping findByShortedUrl(String shortedUrl);
    String  shortTheLink(UrlMappingDTO urlMappingDTO);
}
