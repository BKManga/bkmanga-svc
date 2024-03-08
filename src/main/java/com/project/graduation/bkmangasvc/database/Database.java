package com.project.graduation.bkmangasvc.database;

import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class Database {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                userRepository.saveAll(List.of(
//                        new User("Test", passwordEncoder.encode("test"), "test@gmail.com", "USER", "26/07/2001", "0964343115", 1L, false)
//                ));

                System.out.println("Insert data successfully");
            }
        };
    }
}
