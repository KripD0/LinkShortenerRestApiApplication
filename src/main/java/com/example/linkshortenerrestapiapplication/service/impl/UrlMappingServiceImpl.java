package com.example.linkshortenerrestapiapplication.service.impl;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.exception.NotFoundUrlException;
import com.example.linkshortenerrestapiapplication.exception.NotValidUrlException;
import com.example.linkshortenerrestapiapplication.mapper.UrlMapper;
import com.example.linkshortenerrestapiapplication.model.UrlMapping;
import com.example.linkshortenerrestapiapplication.repository.UrlMappingRepository;
import com.example.linkshortenerrestapiapplication.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UrlMappingServiceImpl implements UrlMappingService {

    //TODO Добавить проверку на присутствие уже несокращенной ссылки.
    //TODO Добавить при создании проверку на уникальность ссылки.

    private final static String staticIpAddress = "http://0.0.0.0:8081/";
    private final UrlMappingRepository urlMappingRepository;
    private final UrlMapper urlMapper;

    @Override
    public UrlMapping findByShortedUrl(String shortedUrl) {
        return urlMappingRepository.findFirstByShortedUrl(shortedUrl)
                .orElseThrow(() -> new NotFoundUrlException("Сокращенная ссылка " + staticIpAddress + shortedUrl + ": недействительна."));
    }

    @Override
    public String shortTheLink(UrlMappingDTO urlMappingDTO) {
        isValidUrl(urlMappingDTO.getUrl());
        UrlMapping urlMapping = urlMapper.convertToUrlMapping(urlMappingDTO);
        String shortedUrl = generateRandomString();
        urlMapping.setShortedUrl(shortedUrl);
        urlMapping.setDateOfCreation(Date.valueOf(LocalDate.now()));
        urlMappingRepository.save(urlMapping);
        return staticIpAddress + shortedUrl;
    }

    private String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[9];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 9);
    }

    private boolean isUrlUnique(String shortedUrl) {
        return urlMappingRepository.findFirstByShortedUrl(shortedUrl)
                .isEmpty();
    }

    private boolean isValidUrl(String userUrl) {
        try {
            URL url = new URL(userUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (MalformedURLException e) {
            throw new NotValidUrlException("Ссылка " + userUrl + " недействительна.\nПредоставьте рабочую ссылку");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
