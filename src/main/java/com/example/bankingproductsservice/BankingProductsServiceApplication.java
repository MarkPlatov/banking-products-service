package com.example.bankingproductsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BankingProductsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingProductsServiceApplication.class, args);
    }

}
