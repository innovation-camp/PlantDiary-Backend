package com.sparta.plantdiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PlantDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantDiaryApplication.class, args);
    }

}
