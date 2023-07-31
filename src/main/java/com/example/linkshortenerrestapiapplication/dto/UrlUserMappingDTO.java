package com.example.linkshortenerrestapiapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlUserMappingDTO  extends UrlMappingDTO{

    private String shortedUrl;
}
