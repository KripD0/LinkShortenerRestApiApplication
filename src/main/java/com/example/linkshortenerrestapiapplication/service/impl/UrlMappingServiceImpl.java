package com.example.linkshortenerrestapiapplication.service.impl;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.mapper.UrlMapper;
import com.example.linkshortenerrestapiapplication.model.UrlMapping;
import com.example.linkshortenerrestapiapplication.repository.UrlMappingRepository;
import com.example.linkshortenerrestapiapplication.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final UrlMapper urlMapper;

    @Override
    public UrlMapping findByShortedUrl(String shortedUrl) {
        return urlMappingRepository.findFirstByShortedUrl(shortedUrl);
    }

    @Override
    public String shortTheLink(UrlMappingDTO urlMappingDTO) {
        UrlMapping urlMapping = urlMapper.convertToUrlMapping(urlMappingDTO);
        String shortedUrl = generateRandomString();
        urlMapping.setShortedUrl(shortedUrl);
        urlMapping.setDateOfCreation(Date.valueOf(LocalDate.now()));
        urlMappingRepository.save(urlMapping);
        return "http://0.0.0.0:8081/" + shortedUrl;
    }

    private String generateRandomString(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[9];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 9);
    }

    private boolean isHashUnique(String hashCode){
        if(urlMappingRepository.findFirstByShortedUrl(hashCode) == null){
            return true;
        }
        return false;
    }
}
