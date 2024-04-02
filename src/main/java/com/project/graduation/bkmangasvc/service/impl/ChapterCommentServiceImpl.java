package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.repository.ChapterCommentRepository;
import com.project.graduation.bkmangasvc.service.ChapterCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChapterCommentServiceImpl implements ChapterCommentService {
    private final ChapterCommentRepository chapterCommentRepository;
}
