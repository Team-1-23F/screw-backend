package com.hongik.ce.f23.team1.screw.config;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class ApplicationConfig implements WebMvcConfigurer {

  final EntityManager em;

}

