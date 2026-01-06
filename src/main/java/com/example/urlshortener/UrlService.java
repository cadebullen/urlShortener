package com.example.urlshortener;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service

public class UrlService {
    private final UrlRepository urlRepository;
    private final StringRedisTemplate redisTemplate;
    private static final String BASE_URL = "http://localhost:8080/api/";

    public UrlService(UrlRepository urlRepository, StringRedisTemplate redisTemplate) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    public UrlDto shortenUrl (String originalUrl){
        // save to database first
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        UrlMapping savedMapping = urlRepository.save(mapping);

        //convert database ID to base62
        String shortCode = Base62Encoder.encode(savedMapping.getId());

        // update the DB entry w/ short code
        savedMapping.setShortCode(shortCode);
        urlRepository.save(savedMapping);

        // cache the mapping in Redis
        redisTemplate.opsForValue().set(shortCode, originalUrl, 1, TimeUnit.DAYS);

        return new UrlDto(shortCode, BASE_URL + shortCode);
    }

    public String getOriginalUrl(String shortCode){
        String cachedUrl = redisTemplate.opsForValue().get(shortCode);
        // check Redis cache first
        if (cachedUrl != null) {
            System.out.println("Cache Hit!");
            return cachedUrl;
        }

        System.out.println("Not in cache, querying DB...");

        // if not in Redis, query DB
        UrlMapping mapping = urlRepository.findByShortCode(shortCode);
        if (mapping != null) {
            // cache in Redis for future requests
            redisTemplate.opsForValue().set(shortCode, mapping.getOriginalUrl(), 1, TimeUnit.DAYS);
            return mapping.getOriginalUrl();
        }

        return null;
    }
}