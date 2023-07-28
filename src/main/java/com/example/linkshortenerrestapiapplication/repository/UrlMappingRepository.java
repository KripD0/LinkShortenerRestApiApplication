package com.example.linkshortenerrestapiapplication.repository;

import com.example.linkshortenerrestapiapplication.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    UrlMapping findFirstByShortedUrl(String shortedUrl);
}
