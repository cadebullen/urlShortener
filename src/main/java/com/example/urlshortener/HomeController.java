package com.example.urlshortener;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "URL Shortener is running! \n" +
               "Use POST /api/shorten to create a link. \n" +
               "Use GET /api/{shortCode} to be redirected.";
    }
}
