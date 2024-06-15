package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreateAuthorRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListAuthorRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateAuthorRequestDTO;
import com.project.graduation.bkmangasvc.entity.Author;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/author")
@CrossOrigin(origins = "*")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping(path = "/all")
    public ApiResponse<List<Author>> getAllAuthor() {
        return authorService.getAllAuthor();
    }

    @PostMapping(path = "/get")
    public ApiResponse<Page<Author>> getListAuthor(
            @Valid @RequestBody GetListAuthorRequestDTO getListAuthorRequestDTO
            ) {
        return authorService.getListAuthor(getListAuthorRequestDTO);
    }

    @PostMapping(path = "/detail")
    public Author getAuthorDetail() {
        return null;
    }

    @PostMapping(path = "/create")
    public ApiResponse<Author> createAuthor(
            @Valid @RequestBody CreateAuthorRequestDTO createAuthorRequestDTO
    ) throws CustomException {
        return authorService.createAuthor(createAuthorRequestDTO);
    }

    @PutMapping(path = "/update")
    public ApiResponse<Author> updateAuthor(
            @Valid @RequestBody UpdateAuthorRequestDTO updateAuthorRequestDTO
    ) throws CustomException {
        return authorService.updateAuthor(updateAuthorRequestDTO);
    }
}
