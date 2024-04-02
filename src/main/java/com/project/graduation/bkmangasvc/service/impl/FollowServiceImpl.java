package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.repository.FollowRepository;
import com.project.graduation.bkmangasvc.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
}
