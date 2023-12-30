package com.example.springprac2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing   // JPA Auditing 활성화 설정
@SpringBootApplication
public class SpringPrac2023Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringPrac2023Application.class, args);
    }

}
