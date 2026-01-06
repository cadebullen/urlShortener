package com.example.urlshortener;

public class UrlDto {
    private String shortCode;
    private String shortUrl;

    public UrlDto(String shortCode, String shortUrl) {
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }
    
    public String getShortUrl() {
        return shortUrl;
    }
}
