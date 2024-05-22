package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.EditPrivacyPolicyRequestDTO;
import com.project.graduation.bkmangasvc.entity.PrivacyPolicy;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

import java.util.List;

public interface PrivacyPolicyService {
    ApiResponse<List<PrivacyPolicy>> getPrivacyPolicy();

    ApiResponse<PrivacyPolicy> editPrivacyPolicy(
            EditPrivacyPolicyRequestDTO privacyPolicyEditRequestDTO,
            Integer id) throws CustomException;
}
