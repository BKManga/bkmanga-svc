package com.project.graduation.bkmangasvc.database;

import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;

@Configuration
@AllArgsConstructor
public class Database {

    private final PrivacyPolicyRepository privacyPolicyRepository;
    private final GenreRepository genreRepository;
    private final GenderRepository genderRepository;
    private final UserStatusRepository userStatusRepository;
    private final AgeRangeRepository ageRangeRepository;

    @Bean
    CommandLineRunner initDatabase() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println("Insert data successfully");
                importData();
            }
        };
    }

    private void importData() throws Exception{
        importGenreData();
        importPrivacyPolicyData();
        importGenderData();
        importUserStatusData();
        importAgeRangeData();
    }

    private CSVReader buildCSVReader(String urlFile) throws Exception {

        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .build();

        return new CSVReaderBuilder(new FileReader("src/main/resources/" + urlFile))
                .withSkipLines(1)
                .withCSVParser(csvParser)
                .build();
    }

    private void importPrivacyPolicyData() throws Exception {
        CSVReader csvReader = buildCSVReader("privacy-policy-seeder.csv");

        String[] nextLine;
        ArrayList<PrivacyPolicy> privacyPolicyArrayList = new ArrayList<>();
        while ((nextLine = csvReader.readNext()) != null) {
            privacyPolicyArrayList.add(
                    new PrivacyPolicy(nextLine[0], nextLine[1], 1L)
            );
        }

        privacyPolicyRepository.saveAll(privacyPolicyArrayList);
    }

    private void importGenreData() throws Exception {
        CSVReader csvReader = buildCSVReader("genre-seeder.csv");

        ArrayList<Genre> genreArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            genreArrayList.add(new Genre(nextLine[0], nextLine[1]));
        }

        genreRepository.saveAll(genreArrayList);
    }

    private void importGenderData() throws Exception {
        CSVReader csvReader = buildCSVReader("gender-seeder.csv");

        ArrayList<Gender> genderArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            genderArrayList.add(new Gender(nextLine[0]));
        }

        genderRepository.saveAll(genderArrayList);
    }

    private void importUserStatusData() throws Exception {
        CSVReader csvReader = buildCSVReader("user-status-seeder.csv");

        ArrayList<UserStatus> userStatusArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            userStatusArrayList.add(new UserStatus(nextLine[0]));
        }

        userStatusRepository.saveAll(userStatusArrayList);
    }

    private void importAgeRangeData() throws Exception {
        CSVReader csvReader = buildCSVReader("age-range-seeder.csv");

        ArrayList<AgeRange> ageRangeArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            ageRangeArrayList.add(new AgeRange(nextLine[0]));
        }

        ageRangeRepository.saveAll(ageRangeArrayList);
    }
}
