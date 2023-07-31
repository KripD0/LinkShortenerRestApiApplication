package com.example.linkshortenerrestapiapplication.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Микросервис по сокращению ссылок.",
                description = "Микросервис, написанный на Spring Boot, представляющий из себя REST API по сокращению ссылок.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Shatokhin Danila",
                        email = "danilkaKashin@mail.ru",
                        url = "https://vk.com/kripdo"
                )
        )
)
public class OpenApiConfig {
}
