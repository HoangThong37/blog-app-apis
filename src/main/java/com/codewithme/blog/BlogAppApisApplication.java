package com.codewithme.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//public class BlogAppApisApplication implements CommandLineRunner {
public class BlogAppApisApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {

        SpringApplication.run(BlogAppApisApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println(passwordEncoder.encode("456"));
//
//    }
}
