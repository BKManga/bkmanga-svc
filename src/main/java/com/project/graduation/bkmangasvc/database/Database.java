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
import java.util.Date;
import java.util.List;
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
    private final AuthorRepository authorRepository;
    private final MangaAuthorRepository mangaAuthorRepository;
    private final ViewMangaRepository viewMangaRepository;
    private final ErrorReportStatusRepository errorReportStatusRepository;
    private final ErrorTypeRepository errorTypeRepository;
    private final OutLawAreaRepository outLawAreaRepository;
    private final OutLawProcessStatusRepository outLawProcessStatusRepository;
    private final OutLawTypeRepository outLawTypeRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                importData();
                System.out.println("Insert data successfully");
            }
        };
    }

    private void importData() throws Exception{
        importGenreData();
        importGenderData();
        importUserStatusData();
        importAgeRangeData();
        importUserData();
        importPrivacyPolicyData();
        importMangaStatusData();
        importMangaData();
        importGenreMangaData();
        importMangaCommentData();
        importChapterData();
        importChapterCommentData();
        importAuthorData();
        importMangaAuthorData();
        importErrorReportStatusData();
        importErrorTypeData();
        importOutLawAreaData();
        importOutLawProcessStatusData();
        importOutLawTypeData();
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
        Optional<User> foundUser = userRepository.findById(2L);
        ArrayList<PrivacyPolicy> privacyPolicyArrayList = new ArrayList<>();

        if (foundUser.isPresent()) {
            while ((nextLine = csvReader.readNext()) != null) {
                privacyPolicyArrayList.add(
                        new PrivacyPolicy(nextLine[0], nextLine[1], foundUser.get())
                );
            }

            privacyPolicyRepository.saveAll(privacyPolicyArrayList);
        }
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
            Optional<User> foundUser = userRepository.findById(updatedBy);

            Manga manga = new Manga();

            if (foundMangaStatus.isPresent() && foundAgeRange.isPresent() && foundUser.isPresent()) {
                manga.setAgeRange(foundAgeRange.get());
                manga.setMangaStatus(foundMangaStatus.get());
                manga.setUpdatedBy(foundUser.get());
                manga.setName(nextLine[0]);
                manga.setOtherName(nextLine[1]);
                manga.setDescription(nextLine[2]);
                manga.setLastChapterUploadAt(new Date());

                mangaArrayList.add(manga);
            }
        }

        mangaRepository.saveAll(mangaArrayList);

        ArrayList<ViewManga> viewMangaArrayList = new ArrayList<>();

        mangaArrayList.forEach(manga -> {
            ViewManga viewManga = new ViewManga(0L, manga);
            viewMangaArrayList.add(viewManga);
        });

        viewMangaRepository.saveAll(viewMangaArrayList);
    }

    private void importGenreMangaData() throws Exception {
        CSVReader csvReader = buildCSVReader("genre-manga-seeder.csv");

        ArrayList<GenreManga> genreMangaArrayList = new ArrayList<>();

        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Optional<Manga> foundManga = mangaRepository.findById(Long.parseLong(nextLine[1]));

            Optional<Genre> foundGenre = genreRepository.findById(Integer.parseInt(nextLine[0]));

            if (foundManga.isPresent() && foundGenre.isPresent()) {
                genreMangaArrayList.add(
                        new GenreManga(foundManga.get(), foundGenre.get())
                );
            }
        }

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
        ArrayList<MangaComment> mangaCommentArrayListTmp = new ArrayList<>();
        ArrayList<MangaComment> mangaCommentArrayList = new ArrayList<>();

        String[] nextLine;

        List<Manga> mangaList = mangaRepository.findAll();

        while ((nextLine = csvReader.readNext()) != null) {
            Optional<User> foundUser = userRepository.findById(Long.parseLong(nextLine[2]));

            if (foundUser.isPresent()) {
                MangaComment mangaComment = new MangaComment();

                mangaComment.setUser(foundUser.get());
                mangaComment.setContent(nextLine[0]);

                mangaCommentArrayListTmp.add(mangaComment);
            }
        }

        mangaList.forEach(manga -> {
            for (MangaComment mangaCommentTmp : mangaCommentArrayListTmp) {
                MangaComment mangaComment = new MangaComment();
                mangaComment.setContent(mangaCommentTmp.getContent());
                mangaComment.setManga(manga);
                mangaComment.setUser(mangaCommentTmp.getUser());

                mangaCommentArrayList.add(mangaComment);
            }
        });

        mangaCommentRepository.saveAll(mangaCommentArrayList);
    }

    private void importChapterData() throws Exception {
        CSVReader csvReader = buildCSVReader("chapter-seeder.csv");
        ArrayList<Chapter> chapterArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Chapter chapter = new Chapter();
            Optional<User> userUploaded = userRepository.findById(Long.parseLong(nextLine[2]));
            Optional<Manga> foundManga = mangaRepository.findById(Long.parseLong(nextLine[1]));

            if (userUploaded.isPresent() && foundManga.isPresent()) {
                chapter.setName(nextLine[0]);
                chapter.setManga(foundManga.get());
                chapter.setUploadedBy(userUploaded.get());

                chapterArrayList.add(chapter);
            }
        }

        chapterRepository.saveAll(chapterArrayList);
    }

    private void importChapterCommentData() throws Exception {
        CSVReader csvReader = buildCSVReader("chapter-comment-seeder.csv");
        ArrayList<ChapterComment> chapterCommentArrayListTmp = new ArrayList<>();
        ArrayList<ChapterComment> chapterCommentArrayList = new ArrayList<>();

        List<Chapter> chapterList = chapterRepository.findAll();

        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null) {
            ChapterComment chapterComment = new ChapterComment();

            Optional<User> foundUser = userRepository.findById(Long.parseLong(nextLine[1]));

            if (foundUser.isPresent()) {
                chapterComment.setUser(foundUser.get());
                chapterComment.setContent(nextLine[0]);
                chapterCommentArrayListTmp.add(chapterComment);
            }
        }

        chapterList.forEach(chapter -> {
            for (ChapterComment chapterCommentTmp : chapterCommentArrayListTmp) {
                ChapterComment chapterComment = new ChapterComment();
                chapterComment.setContent(chapterCommentTmp.getContent());
                chapterComment.setChapter(chapter);
                chapterComment.setUser(chapterCommentTmp.getUser());
                chapterCommentArrayList.add(chapterComment);
            }
        });

        chapterCommentRepository.saveAll(chapterCommentArrayList);
    }

    private void importAuthorData() throws Exception {
        CSVReader csvReader = buildCSVReader("author-seeder.csv");
        ArrayList<Author> authorArrayList = new ArrayList<>();

        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Author author = new Author();
            author.setName(nextLine[0]);
            authorArrayList.add(author);
        }

        authorRepository.saveAll(authorArrayList);
    }

    private void importMangaAuthorData() throws Exception {
        CSVReader csvReader = buildCSVReader("manga-author-seeder.csv");
        ArrayList<MangaAuthor> mangaAuthorArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Optional<Manga> foundManga = mangaRepository.findById(Long.parseLong(nextLine[1]));
            Optional<Author> foundAuthor = authorRepository.findById(Integer.parseInt(nextLine[0]));

            if (foundManga.isPresent() && foundAuthor.isPresent()) {
                MangaAuthor mangaAuthor = new MangaAuthor();
                mangaAuthor.setAuthor(foundAuthor.get());
                mangaAuthor.setManga(foundManga.get());

                mangaAuthorArrayList.add(mangaAuthor);
            }
        }

        mangaAuthorRepository.saveAll(mangaAuthorArrayList);
    }

    private void importErrorReportStatusData() throws Exception {
        CSVReader csvReader = buildCSVReader("error-report-status-seeder.csv");
        ArrayList<ErrorReportStatus> errorReportStatusArrayList = new ArrayList<>();
        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null) {
            ErrorReportStatus errorReportStatus = new ErrorReportStatus();
            errorReportStatus.setName(nextLine[0]);
            errorReportStatusArrayList.add(errorReportStatus);
        }

        errorReportStatusRepository.saveAll(errorReportStatusArrayList);
    }

    private void importErrorTypeData() throws Exception {
        CSVReader csvReader = buildCSVReader("error-type-seeder.csv");
        ArrayList<ErrorType> errorTypeArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            ErrorType errorType = new ErrorType();
            errorType.setName(nextLine[0]);
            errorTypeArrayList.add(errorType);
        }

        errorTypeRepository.saveAll(errorTypeArrayList);
    }

    private void importOutLawAreaData() throws Exception {
        CSVReader csvReader = buildCSVReader("out-law-area-seeder.csv");
        ArrayList<OutLawArea> outLawAreaArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            OutLawArea outLawArea = new OutLawArea();
            outLawArea.setName(nextLine[0]);
            outLawAreaArrayList.add(outLawArea);
        }

        outLawAreaRepository.saveAll(outLawAreaArrayList);
    }

    private void importOutLawProcessStatusData() throws Exception {
        CSVReader csvReader = buildCSVReader("out-law-process-status-seeder.csv");
        ArrayList<OutLawProcessStatus> outLawProcessStatusArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            OutLawProcessStatus outLawProcessStatus = new OutLawProcessStatus();
            outLawProcessStatus.setName(nextLine[0]);
            outLawProcessStatusArrayList.add(outLawProcessStatus);
        }

        outLawProcessStatusRepository.saveAll(outLawProcessStatusArrayList);
    }

    private void importOutLawTypeData() throws Exception {
        CSVReader csvReader = buildCSVReader("out-law-type-seeder.csv");
        ArrayList<OutLawType> outLawTypeArrayList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            OutLawType outLawType = new OutLawType();
            outLawType.setName(nextLine[0]);
            outLawTypeArrayList.add(outLawType);
        }

        outLawTypeRepository.saveAll(outLawTypeArrayList);
    }
}
