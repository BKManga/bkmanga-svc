package com.project.graduation.bkmangasvc.database;

import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Database {

    @Bean
    CommandLineRunner initDatabase(MangaStatusRepository repository) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println("Insert data successfully");
            }
        };
    }
}
