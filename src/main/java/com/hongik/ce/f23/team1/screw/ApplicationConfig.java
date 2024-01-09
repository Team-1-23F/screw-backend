package com.hongik.ce.f23.team1.screw;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  private final EntityManager em;

  public ApplicationConfig(EntityManager em) {
    this.em = em;
  }
}

