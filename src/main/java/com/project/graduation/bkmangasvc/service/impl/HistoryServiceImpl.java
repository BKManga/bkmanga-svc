package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.repository.HistoryRepository;
import com.project.graduation.bkmangasvc.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
}
