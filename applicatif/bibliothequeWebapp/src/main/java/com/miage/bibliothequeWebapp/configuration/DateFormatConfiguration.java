package com.miage.bibliothequeWebapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class DateFormatConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Convertisseur pour parser les dates datetime-local en objets Date
        registry.addConverter(String.class, Date.class, source -> {
            if (source == null || source.isEmpty()) {
                return null;
            }
            try {
                // Format ISO datetime-local: 2025-11-30T20:30
                LocalDateTime ldt = LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return java.sql.Timestamp.valueOf(ldt);
            } catch (Exception e) {
                // Fallback : essayer le format timestamp
                try {
                    return new Date(Long.parseLong(source));
                } catch (Exception e2) {
                    return null;
                }
            }
        });
    }
}
