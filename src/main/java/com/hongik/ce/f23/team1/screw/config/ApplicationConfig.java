package com.hongik.ce.f23.team1.screw.config;

import com.hongik.ce.f23.team1.screw.global.interceptor.AuthInterceptor;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

  public ApplicationConfig(EntityManager em) {
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new AuthInterceptor())
        .order(1)
        .addPathPatterns("/**")
        // TODO: 요구사항 검토 필요
        .excludePathPatterns("/auth/login", "/auth/join");
  }

}

