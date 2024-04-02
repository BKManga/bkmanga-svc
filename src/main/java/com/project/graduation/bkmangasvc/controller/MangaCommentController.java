package com.project.graduation.bkmangasvc.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/comment/manga")
@CrossOrigin(origins = "*")
public class MangaCommentController {
}
