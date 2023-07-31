package com.example.linkshortenerrestapiapplication.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "url_mapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "url", length = 1024)
    private String url;

    @Column(name = "shorted_url")
    private String shortedUrl;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_creation")
    private Date dateOfCreation;
}
