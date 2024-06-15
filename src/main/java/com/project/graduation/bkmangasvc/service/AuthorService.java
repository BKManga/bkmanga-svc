package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateAuthorRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListAuthorRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateAuthorRequestDTO;
import com.project.graduation.bkmangasvc.entity.Author;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {
    ApiResponse<List<Author>> getAllAuthor();

    ApiResponse<Page<Author>> getListAuthor(GetListAuthorRequestDTO getListAuthorRequestDTO);

    ApiResponse<Author> createAuthor(CreateAuthorRequestDTO createAuthorRequestDTO) throws CustomException;

    ApiResponse<Author> updateAuthor(UpdateAuthorRequestDTO updateAuthorRequestDTO) throws CustomException;
}
