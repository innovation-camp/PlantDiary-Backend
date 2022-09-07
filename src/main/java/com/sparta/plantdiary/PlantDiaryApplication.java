package com.sparta.plantdiary;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PlantDiaryApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:aws.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(PlantDiaryApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
