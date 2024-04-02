package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.repository.MangaCommentRepository;
import com.project.graduation.bkmangasvc.service.MangaCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MangaCommentServiceImpl implements MangaCommentService {
    private final MangaCommentRepository mangaCommentRepository;
}
