package com.project.graduation.bkmangasvc.config;

import com.project.graduation.bkmangasvc.security.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] publicEndPoints = getPublicEndPoints();

    @Bean
    AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicEndPoints).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAfter(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] getPublicEndPoints() {

        final List<String> publicDocEndPoints = List.of(
                "api/v1/manga/detail",
                "v3/api-docs",
                "v3/api-docs.yaml"
        );

        final List<String> publicMangaEndPoints = List.of(
                "api/v1/manga/search/by/name",
                "api/v1/manga/get",
                "api/v1/manga/get/lastUpload",
                "api/v1/manga/search/by/genre",
                "api/v1/manga/search/by/author",
                "api/v1/manga/search/by/filter"
        );

        final List<String> publicChapterEndPoints = List.of(
                "api/v1/chapter/get/detail"
        );

        final List<String> publicMangaCommentEndPoints = List.of(
                "api/v1/comment/manga/get"
        );

        final List<String> publicChapterCommentEndPoints = List.of(
                "api/v1/comment/chapter/get",
                "api/v1/comment/chapter/detail"
        );

        final List<String> publicGenreEndPoints = List.of(
                "api/v1/genre/all",
                "api/v1/genre/{id}"
        );

        final List<String> publicAuthEndPoints = List.of(
                "api/v1/auth/login",
                "api/v1/register"
        );

        final List<String> publicPrivacyPolicyEndPoints = List.of(
                "api/v1/privacyPolicy/all"
        );

        final List<String> publicAuthorEndPoints = List.of(
                "api/v1/author/all",
                "api/v1/author/get",
                "api/v1/author/detail"
        );

        final List<String> publicFileHandleEndPoints = List.of(
                "api/v1/file/manga/image-logo/{mangaId}",
                "api/v1/file/manga/image-large/{mangaId}",
                "api/v1/file/image/manga/{mangaId}/chapter/{chapterId}/{imageName:.+}",
                "api/v1/file/user/profile/{userId}",
                "api/v1/file/user/profile",
                "api/v1/file/image/chapter/all"
        );

        return Stream.of(
                publicDocEndPoints,
                publicMangaEndPoints,
                publicChapterEndPoints,
                publicMangaCommentEndPoints,
                publicChapterCommentEndPoints,
                publicGenreEndPoints,
                publicAuthEndPoints,
                publicPrivacyPolicyEndPoints,
                publicAuthorEndPoints,
                publicFileHandleEndPoints
        ).flatMap(Collection::stream).toList().toArray(String[]::new);
    }
}
