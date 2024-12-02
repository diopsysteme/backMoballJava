package org.example.backmobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan("org.example.backmobile.Datas.Repository")
@EnableJpaRepositories("org.example.backmobile.Datas.Entity")
public class BackmobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackmobileApplication.class, args);
    }

}
