package com.example.linkshortenerrestapiapplication.controller;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.service.impl.UrlMappingServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UrlShorterController {

    private final UrlMappingServiceImpl urlMappingService;

    @GetMapping("/short")
    public String shortTheUrl(@RequestBody UrlMappingDTO urlMappingDTO){
        return urlMappingService.shortTheLink(urlMappingDTO);
    }

    @GetMapping("/{shortedUrl}")
    public RedirectView redirectToUrl(@PathVariable String shortedUrl)  {
        String originalUrl = urlMappingService.findByShortedUrl(shortedUrl).getUrl();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }
}
