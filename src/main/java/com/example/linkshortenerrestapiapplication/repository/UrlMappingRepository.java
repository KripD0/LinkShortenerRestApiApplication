package com.example.linkshortenerrestapiapplication.repository;

import com.example.linkshortenerrestapiapplication.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findFirstByShortedUrl(String shortedUrl);
    Optional<UrlMapping> findFirstByUrl(String url);
    void deleteByDateOfCreationBefore(Date date);
}
