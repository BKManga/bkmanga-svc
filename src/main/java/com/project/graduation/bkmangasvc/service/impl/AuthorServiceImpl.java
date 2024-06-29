package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.entity.Author;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.AuthorRepository;
import com.project.graduation.bkmangasvc.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public ApiResponse<List<Author>> getAllAuthor() {
        List<Author> authorList = authorRepository.findAll();
        return ApiResponse.successWithResult(authorList);
    }

    @Override
    public ApiResponse<Page<Author>> getListAuthor(
        GetListAuthorRequestDTO getListAuthorRequestDTO
    ) {
        Pageable pageable = PageRequest.of(
                getListAuthorRequestDTO.getPage(),
                getListAuthorRequestDTO.getSize()
        );

        Page<Author> authorPage = authorRepository.findByOrderByNameAsc(pageable);

        return ApiResponse.successWithResult(authorPage);
    }

    @Override
    public ApiResponse<Author> getAuthorDetail(
            GetAuthorDetailRequestDTO getAuthorDetailRequestDTO
    ) throws CustomException {
        Author author = getAuthorValue(getAuthorDetailRequestDTO.getAuthorId());

        return ApiResponse.successWithResult(author);
    }

    @Override
    public ApiResponse<Page<Author>> getListAuthorByName(
            GetListAuthorByNameDTO getListAuthorByNameDTO
    ) {
        Pageable pageable = PageRequest.of(
                getListAuthorByNameDTO.getPage(),
                getListAuthorByNameDTO.getSize()
        );

        Page<Author> authorPage = authorRepository.findByNameContainingOrderByNameAsc(getListAuthorByNameDTO.getName(), pageable);

        return ApiResponse.successWithResult(authorPage);
    }

    @Override
    public ApiResponse<Author> createAuthor(CreateAuthorRequestDTO createAuthorRequestDTO) throws CustomException {

        String nameAuthor = createAuthorRequestDTO.getName().toLowerCase(Locale.ROOT);

        checkAuthorValue(nameAuthor);

        Author author = new Author(nameAuthor);

        authorRepository.save(author);
        return ApiResponse.successWithResult(author);
    }

    @Override
    public ApiResponse<Author> updateAuthor(UpdateAuthorRequestDTO updateAuthorRequestDTO) throws CustomException {

        String nameAuthor = updateAuthorRequestDTO.getName().toLowerCase(Locale.ROOT);

        Author author = getAuthorValue(updateAuthorRequestDTO.getAuthorId());

        checkAuthorValue(author, nameAuthor);

        author.setName(updateAuthorRequestDTO.getName());
        authorRepository.save(author);

        return ApiResponse.successWithResult(author);
    }

    private void checkAuthorValue(String nameAuthor) throws CustomException {
        Optional<Author> author = authorRepository.findByName(nameAuthor);

        if (author.isPresent()) {
            throw new CustomException(ErrorCode.AUTHOR_EXISTED);
        }
    }

    private void checkAuthorValue(Author author, String nameAuthor) throws CustomException {
        Optional<Author> foundAuthor = authorRepository.findByName(nameAuthor);

        if (foundAuthor.isPresent()) {
            if (!Objects.equals(foundAuthor.get().getId(), author.getId())) {
                throw new CustomException(ErrorCode.AUTHOR_NAME_EXISTED);
            }
        }
    }

    private Author getAuthorValue(Integer authorId) throws CustomException {
        Optional<Author> author = authorRepository.findById(authorId);

        if (author.isEmpty()) {
            throw new CustomException(ErrorCode.AUTHOR_NOT_EXIST);
        }

        return author.get();
    }
}
