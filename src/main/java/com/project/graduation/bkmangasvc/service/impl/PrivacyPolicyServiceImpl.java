package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.dto.request.EditPrivacyPolicyRequestDTO;
import com.project.graduation.bkmangasvc.entity.PrivacyPolicy;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.PrivacyPolicyRepository;
import com.project.graduation.bkmangasvc.service.PrivacyPolicyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrivacyPolicyServiceImpl implements PrivacyPolicyService {
    private final PrivacyPolicyRepository privacyPolicyRepository;


    @Override
    public ApiResponse<List<PrivacyPolicy>> getPrivacyPolicy() {
        List<PrivacyPolicy> privacyPolicyList = privacyPolicyRepository.findAll();

        return ApiResponse.successWithResult(privacyPolicyList);
    }

    @Override
    public ApiResponse<PrivacyPolicy> editPrivacyPolicy(
            EditPrivacyPolicyRequestDTO privacyPolicyEditRequestDTO
    ) throws CustomException{

        PrivacyPolicy privacyPolicy = getPrivacyPolicyValue(privacyPolicyEditRequestDTO.getId());

        privacyPolicy.setQuestion(privacyPolicyEditRequestDTO.getQuestion());
        privacyPolicy.setAnswer(privacyPolicyEditRequestDTO.getAnswer());
        privacyPolicy.setUpdatedBy(privacyPolicyEditRequestDTO.getUpdatedBy());

        privacyPolicyRepository.save(privacyPolicy);

        return ApiResponse.successWithResult(privacyPolicy);
    }

    private PrivacyPolicy getPrivacyPolicyValue(Integer id) throws CustomException{
        Optional<PrivacyPolicy> foundPrivacyPolicy = privacyPolicyRepository.findById(id);

        if (foundPrivacyPolicy.isEmpty()) {
            throw new CustomException(ErrorCode.RECORD_NOT_EXIST);
        }

        return foundPrivacyPolicy.get();
    }
}
