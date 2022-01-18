package com.example.securitystudyclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecurityStudyClubApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityStudyClubApplication.class, args);
  }

}
