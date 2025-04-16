package com.web.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/soa/**")
                        .allowedOrigins("http://localhost:4201", "http://localhost:8080")
                        .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH");

                registry.addMapping("/ws/**")
                        .allowedOrigins("*");
            }
        };
    }
}
