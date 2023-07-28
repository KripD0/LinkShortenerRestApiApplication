package com.example.linkshortenerrestapiapplication.controller;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.exception.NotFoundUrlException;
import com.example.linkshortenerrestapiapplication.exception.NotValidUrlException;
import com.example.linkshortenerrestapiapplication.service.impl.UrlMappingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UrlShorterController {

    //TODO Добавить Scheduled метод, который будет вызываться 1 раз в день и будет удалять ссылки которым больше 7 дней.
    // Создать кастомный Exception добавить ответ на неправильную ссылку.
    // Добавить метод контроллера который будет принимать не только длинную ссылнку, но и то что хочет получить пользователь,
    // проверять на уникальность и если что возвращать json о том что такая ссылка уже существует.
    // Добавить проверку работоспособности ссылки которую присылает пользователь.

    private final UrlMappingServiceImpl urlMappingService;

    @GetMapping("/short")
    public String shortTheUrl(@RequestBody UrlMappingDTO urlMappingDTO) {
        return urlMappingService.shortTheLink(urlMappingDTO);
    }

    @GetMapping("/{shortedUrl}")
    public RedirectView redirectToUrl(@PathVariable String shortedUrl) {
        String originalUrl = urlMappingService.findByShortedUrl(shortedUrl).getUrl();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }

    @ExceptionHandler(NotFoundUrlException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundUrlException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NotValidUrlException.class)
    public ResponseEntity<?> handleNotValidException(NotValidUrlException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
