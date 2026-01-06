package com.example.urlshortener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urlmapping")

public class UrlMapping{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalUrl;
    
    @Column(unique = true)
    private String shortCode;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiresAt;

    public UrlMapping(){}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl){
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode (String shortCode){
        this.shortCode = shortCode;
    }   
}