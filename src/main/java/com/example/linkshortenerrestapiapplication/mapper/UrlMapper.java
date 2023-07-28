package com.example.linkshortenerrestapiapplication.mapper;

import com.example.linkshortenerrestapiapplication.dto.UrlMappingDTO;
import com.example.linkshortenerrestapiapplication.model.UrlMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMapper {

    UrlMapping convertToUrlMapping(UrlMappingDTO urlMappingDTO);
    UrlMappingDTO convertToUrlMappingDTO(UrlMapping urlMapping);
}
