package com.hongik.ce.f23.team1.screw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ScrewApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScrewApplication.class, args);
  }

}
