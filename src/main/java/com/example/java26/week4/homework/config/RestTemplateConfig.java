package com.example.java26.week4.homework.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
public class RestTemplateConfig {
    private final String url = "https://reqres.in/api/users";
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
