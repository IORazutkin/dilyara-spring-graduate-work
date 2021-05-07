package com.dilyara.graduatework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class GraduateWorkApplication {

  public static void main (String[] args) {
    SpringApplication application = new SpringApplication(GraduateWorkApplication.class);
    application.setAdditionalProfiles("ssl");
    application.run(args);
  }

}
