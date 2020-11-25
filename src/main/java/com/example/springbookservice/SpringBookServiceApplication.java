package com.example.springbookservice;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBookServiceApplication {

    public static void main(String[] args) {
        ElasticApmAttacher.attach();
        SpringApplication.run(SpringBookServiceApplication.class, args);
    }

}
