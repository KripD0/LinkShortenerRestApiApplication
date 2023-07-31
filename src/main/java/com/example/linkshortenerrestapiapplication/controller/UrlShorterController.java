package com.example.linkshortenerrestapiapplication.controller;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.dto.UrlUserMappingDTO;
import com.example.linkshortenerrestapiapplication.exception.NotFoundUrlException;
import com.example.linkshortenerrestapiapplication.exception.NotValidUrlException;
import com.example.linkshortenerrestapiapplication.exception.response.UrlErrorResponse;
import com.example.linkshortenerrestapiapplication.service.impl.UrlMappingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "Основной контроллер.",
        description = """
        Данный контролер отвечает за сокращение ссылок и
        "перенаправление на оригинальные.""")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UrlShorterController {

    private final UrlMappingServiceImpl urlMappingService;

    @Operation(summary = "Сокращение ссылки",
    description = """
            Этот метод контроллера принимает URL и сокращает его до 9 символов, возвращая его пользователю.
            """)
    @PostMapping("/short")
    @ResponseStatus(HttpStatus.CREATED)
    public String shortTheUrl(@RequestBody UrlMappingDTO urlMappingDTO) {
        isValidUrl(urlMappingDTO);
        return urlMappingService.shortTheLink(urlMappingDTO);
    }

    @Operation(summary = "Сокращение пользовательской ссылки",
            description = """
            Этот метод контроллера принимает URL и сокращенный URL который хочет пользователь,
             и возвращает сокращенный URL с доменом.
            """)
    @GetMapping("/{shortedUrl}")
    @ResponseStatus(HttpStatus.FOUND)
    public RedirectView redirectToUrl(@PathVariable String shortedUrl) {
        String originalUrl = urlMappingService.findByShortedUrl(shortedUrl).getUrl();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }

    @Operation(summary = "Перенаправление на оригинальный сайт",
    description = """
            Этот метод контроллера принимает на вход сокращенную ссылку с доменом 
            и перенаправляет пользователя на нужный ему сайт
            """)
    @PostMapping("/userShort")
    @ResponseStatus(HttpStatus.CREATED)
    public String shortTheUserUrl(@RequestBody UrlUserMappingDTO urlUserMappingDTO){
        isValidUrl(urlUserMappingDTO);
        return urlMappingService.createUserUrl(urlUserMappingDTO);
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

    private void isValidUrl(UrlMappingDTO urlMappingDTO) {
        if (!urlMappingDTO.getUrl().startsWith("http://") && !urlMappingDTO.getUrl().startsWith("https://")) {
            urlMappingDTO.setUrl("http://" + urlMappingDTO.getUrl());
        }

        UrlValidator urlValidator = new UrlValidator();
        if(!urlValidator.isValid(urlMappingDTO.getUrl())){
            throw new NotValidUrlException("Ссылка " + urlMappingDTO.getUrl() + " недействительна.\nПредоставьте рабочую ссылку");
        }
    }
}
