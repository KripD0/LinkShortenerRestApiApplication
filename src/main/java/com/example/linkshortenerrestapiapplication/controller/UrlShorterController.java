package com.example.linkshortenerrestapiapplication.controller;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.exception.NotFoundUrlException;
import com.example.linkshortenerrestapiapplication.exception.NotValidUrlException;
import com.example.linkshortenerrestapiapplication.exception.response.UrlErrorResponse;
import com.example.linkshortenerrestapiapplication.service.impl.UrlMappingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UrlShorterController {

    //TODO Добавить Scheduled метод, который будет вызываться 1 раз в день и будет удалять ссылки которым больше 7 дней.
    // Добавить метод контроллера который будет принимать не только длинную ссылнку, но и то что хочет получить пользователь,
    // проверять на уникальность и если что возвращать json о том что такая ссылка уже существует.

    private final UrlMappingServiceImpl urlMappingService;

    @GetMapping("/short")
    @ResponseStatus(HttpStatus.OK)
    public String shortTheUrl(@RequestBody UrlMappingDTO urlMappingDTO) {
        return urlMappingService.shortTheLink(urlMappingDTO);
    }

    @GetMapping("/{shortedUrl}")
    @ResponseStatus(HttpStatus.OK)
    public RedirectView redirectToUrl(@PathVariable String shortedUrl) {
        String originalUrl = urlMappingService.findByShortedUrl(shortedUrl).getUrl();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }

    @ExceptionHandler(NotFoundUrlException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UrlErrorResponse handleNotFoundException(NotFoundUrlException e) {
        return new UrlErrorResponse(
                "URL NOT FOUND: " + e.getMessage(),
                System.currentTimeMillis());
    }

    @ExceptionHandler(NotValidUrlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UrlErrorResponse handleNotValidException(NotValidUrlException e) {
        return new UrlErrorResponse(
                "URL NOT VALID: " + e.getMessage(),
                System.currentTimeMillis());
    }
}
