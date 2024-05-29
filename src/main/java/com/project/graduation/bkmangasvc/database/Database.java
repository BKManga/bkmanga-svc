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
import java.util.Optional;

@Configuration
@AllArgsConstructor
public class Database {

    private final PrivacyPolicyRepository privacyPolicyRepository;
    private final GenreRepository genreRepository;
    private final GenderRepository genderRepository;
    private final UserStatusRepository userStatusRepository;
    private final AgeRangeRepository ageRangeRepository;
    private final MangaStatusRepository mangaStatusRepository;
    private final MangaRepository mangaRepository;
    private final GenreMangaRepository genreMangaRepository;
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;
    private final MangaCommentRepository mangaCommentRepository;
    private final ChapterRepository chapterRepository;
    private final ChapterCommentRepository chapterCommentRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                importData();
                System.out.println("Insert data successfully");
            }
        };
    }

    private void importData() throws Exception{
//        importGenreData();
//        importPrivacyPolicyData();
//        importGenderData();
//        importUserStatusData();
//        importAgeRangeData();
//        importUserData();
//        importMangaStatusData();
//        importMangaData();
//        importGenreMangaData();
//        importMangaCommentData();
//        importChapterData();
//        importChapterCommentData();
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

    private void importMangaData() throws Exception {
        CSVReader csvReader = buildCSVReader("manga-seeder.csv");

        ArrayList<Manga> mangaArrayList = new ArrayList<>();

        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Long updatedBy = Long.parseLong(nextLine[3]);
            Integer mangaStatusId = Integer.parseInt(nextLine[4]);
            Integer ageRangeId = Integer.parseInt(nextLine[5]);

            Optional<MangaStatus> foundMangaStatus = mangaStatusRepository.findById(mangaStatusId);
            Optional<AgeRange> foundAgeRange = ageRangeRepository.findById(ageRangeId);

            Manga manga = new Manga();

            if (foundMangaStatus.isPresent() && foundAgeRange.isPresent()) {
                manga.setAgeRange(foundAgeRange.get());
                manga.setMangaStatus(foundMangaStatus.get());
                manga.setUpdatedBy(updatedBy);
                manga.setName(nextLine[0]);
                manga.setOtherName(nextLine[1]);
                manga.setDescription(nextLine[2]);

                mangaArrayList.add(manga);
            }
        }

        mangaRepository.saveAll(mangaArrayList);
    }

    private void importGenreMangaData() throws Exception {
        CSVReader csvReader = buildCSVReader("genre-manga-seeder.csv");

        ArrayList<GenreManga> genreMangaArrayList = new ArrayList<>();

        Long cursor = 1L;
        Optional<Manga> manga = mangaRepository.findById(cursor);
        ArrayList<Integer> genreIdArrayList = new ArrayList<>();

        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            genreIdArrayList.add(Integer.parseInt(nextLine[0]));
        }

        genreRepository.findAllById(genreIdArrayList).forEach(genre -> {
            genreMangaArrayList.add(
                    new GenreManga(
                            manga.get(),
                            genre
                    )
            );
        });

        genreMangaRepository.saveAll(genreMangaArrayList);
    }

    private void importMangaStatusData() throws Exception {
        CSVReader csvReader = buildCSVReader("manga-status-seeder.csv");
        ArrayList<MangaStatus> mangaStatusArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            mangaStatusArrayList.add(
                    new MangaStatus(nextLine[0])
            );
        }

        mangaStatusRepository.saveAll(mangaStatusArrayList);
    }

    private void importUserData() throws Exception {
        CSVReader csvReader = buildCSVReader("user-seeder.csv");
        ArrayList<User> userArrayList = new ArrayList<>();
        ArrayList<Level> levelArrayList = new ArrayList<>();
        String[] nextLine;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        while ((nextLine = csvReader.readNext()) != null) {
            User user = new User();
            user.setUsername(nextLine[0]);
            user.setFullName(nextLine[1]);
            user.setPassword(passwordEncoder.encode(nextLine[2]));
            user.setEmail(nextLine[3]);
            user.setRole(nextLine[4]);
            user.setDateOfBirth(nextLine[5]);
            user.setPhoneNumber(nextLine[6]);
            Optional<Gender> foundGender = genderRepository.findById(Integer.parseInt(nextLine[7]));
            Optional<UserStatus> foundUserStatus = userStatusRepository.findById(Integer.parseInt(nextLine[8]));

            if (foundGender.isPresent() && foundUserStatus.isPresent()) {
                user.setGender(foundGender.get());
                user.setUserStatus(foundUserStatus.get());
                userArrayList.add(user);
            }

            userRepository.saveAll(userArrayList);
        }

        userArrayList.forEach(userEle -> {
            levelArrayList.add(
                    new Level(userEle, 0L)
            );
        });

        levelRepository.saveAll(levelArrayList);
    }

    private void importMangaCommentData() throws Exception {
        CSVReader csvReader = buildCSVReader("manga-comment-seeder.csv");
        ArrayList<MangaComment> mangaCommentArrayList = new ArrayList<>();
        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null) {
            Optional<User> foundUser = userRepository.findById(Long.parseLong(nextLine[2]));
            Optional<Manga> foundManga = mangaRepository.findById(Long.parseLong(nextLine[1]));

            if (foundUser.isPresent() && foundManga.isPresent()) {
                MangaComment mangaComment = new MangaComment();

                mangaComment.setUser(foundUser.get());
                mangaComment.setManga(foundManga.get());
                mangaComment.setContent(nextLine[0]);

                mangaCommentArrayList.add(mangaComment);
            }
        }

        mangaCommentRepository.saveAll(mangaCommentArrayList);
    }

    private void importChapterData() throws Exception {
        CSVReader csvReader = buildCSVReader("chapter-seeder.csv");
        ArrayList<Chapter> chapterArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Chapter chapter = new Chapter();
            Optional<User> userUploaded = userRepository.findById(Long.parseLong(nextLine[1]));
            Optional<Manga> foundManga = mangaRepository.findById(Long.parseLong(nextLine[2]));

            if (userUploaded.isPresent() && foundManga.isPresent()) {
                chapter.setName(nextLine[0]);
                chapter.setManga(foundManga.get());
                chapter.setUploadedBy(Long.parseLong(nextLine[1]));

                chapterArrayList.add(chapter);
            }
        }

        chapterRepository.saveAll(chapterArrayList);
    }

    private void importChapterCommentData() throws Exception {
        CSVReader csvReader = buildCSVReader("chapter-comment-seeder.csv");
        ArrayList<ChapterComment> chapterCommentArrayList = new ArrayList<>();

        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null) {
            ChapterComment chapterComment = new ChapterComment();

            Optional<User> foundUser = userRepository.findById(Long.parseLong(nextLine[2]));
            Optional<Chapter> foundChapter = chapterRepository.findById(Long.parseLong(nextLine[1]));

            if (foundUser.isPresent() && foundChapter.isPresent()) {
                chapterComment.setUser(foundUser.get());
                chapterComment.setChapter(foundChapter.get());
                chapterComment.setContent(nextLine[0]);
                chapterCommentArrayList.add(chapterComment);
            }
        }

        chapterCommentRepository.saveAll(chapterCommentArrayList);
    }
}
