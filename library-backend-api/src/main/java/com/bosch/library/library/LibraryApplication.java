package com.bosch.library.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableFeignClients
@SpringBootApplication
public class LibraryApplication {

    public static void main(final String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}
