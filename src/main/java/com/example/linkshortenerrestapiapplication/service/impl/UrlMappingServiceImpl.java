package com.example.linkshortenerrestapiapplication.service.impl;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.dto.UrlUserMappingDTO;
import com.example.linkshortenerrestapiapplication.exception.NotFoundUrlException;
import com.example.linkshortenerrestapiapplication.mapper.UrlMapper;
import com.example.linkshortenerrestapiapplication.model.UrlMapping;
import com.example.linkshortenerrestapiapplication.repository.UrlMappingRepository;
import com.example.linkshortenerrestapiapplication.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlMappingServiceImpl implements UrlMappingService {

    private final static String staticIpAddress = "http://192.168.111.102:8080/";
    private final UrlMappingRepository urlMappingRepository;
    private final UrlMapper urlMapper;

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void deleteUrl(){
        Date cutoffDate = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        urlMappingRepository.deleteByDateOfCreationBefore(cutoffDate);
    }

    @Override
    public UrlMapping findByShortedUrl(String shortedUrl) {
        return urlMappingRepository.findFirstByShortedUrl(shortedUrl)
                .orElseThrow(() -> new NotFoundUrlException("Сокращенная ссылка " + staticIpAddress + shortedUrl + ": недействительна."));
    }

    @Override
    public String shortTheLink(UrlMappingDTO urlMappingDTO) {
        String shortedUrl = isThisUrlAlreadyExists(urlMappingDTO.getUrl());
        UrlMapping urlMapping = urlMapper.convertToUrlMapping(urlMappingDTO);

        if(shortedUrl == null) {
            shortedUrl = generateRandomString();
            while (!isUrlUnique(shortedUrl)){
                shortedUrl = generateRandomString();
            }
        }

        urlMapping.setShortedUrl(shortedUrl);
        urlMapping.setDateOfCreation(Date.valueOf(LocalDate.now()));
        urlMappingRepository.save(urlMapping);
        return staticIpAddress + shortedUrl;
    }

    @Override
    public String createUserUrl(UrlUserMappingDTO urlUserMappingDTO){
        String shortedUrl = isThisUrlAlreadyExists(urlUserMappingDTO.getUrl());
        if(shortedUrl == null){
            shortedUrl = urlUserMappingDTO.getShortedUrl();
            UrlMapping urlMapping = UrlMapping.builder()
                    .url(urlUserMappingDTO.getUrl())
                    .shortedUrl(shortedUrl)
                    .dateOfCreation(Date.valueOf(LocalDate.now()))
                    .build();
            urlMappingRepository.save(urlMapping);
        }
        return staticIpAddress + shortedUrl;
    }

    private String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[9];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes)
                .substring(0, 9);
    }

    private boolean isUrlUnique(String shortedUrl) {
        return urlMappingRepository.findFirstByShortedUrl(shortedUrl)
                .isEmpty();
    }

    private String isThisUrlAlreadyExists(String url){
        Optional<UrlMapping> shortedUrl = urlMappingRepository.findFirstByUrl(url);
        return shortedUrl.map(UrlMapping::getShortedUrl)
                .orElse(null);
    }
}
