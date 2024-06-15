package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Page<Author> findByOrderByNameAsc(Pageable pageable);

    List<Author> findByIdIn(List<Integer> listAuthorId);

    Optional<Author> findByName(String name);
}
