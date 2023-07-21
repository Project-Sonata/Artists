package com.odeyalo.sonata.artists;

import com.odeyalo.sonata.suite.reactive.annotation.EnableSuiteReactive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSuiteReactive
public class ArtistsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtistsApplication.class, args);
    }
}
