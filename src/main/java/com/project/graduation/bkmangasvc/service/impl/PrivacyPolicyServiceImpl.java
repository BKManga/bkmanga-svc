package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreatePrivacyPolicyRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetPrivacyPolicyDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdatePrivacyPolicyRequestDTO;
import com.project.graduation.bkmangasvc.entity.PrivacyPolicy;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.PrivacyPolicyRepository;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.PrivacyPolicyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrivacyPolicyServiceImpl implements PrivacyPolicyService {
    private final PrivacyPolicyRepository privacyPolicyRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;


    @Override
    public ApiResponse<List<PrivacyPolicy>> getPrivacyPolicy() {
        List<PrivacyPolicy> privacyPolicyList = privacyPolicyRepository.findAll();

        return ApiResponse.successWithResult(privacyPolicyList);
    }

    @Override
    public ApiResponse<PrivacyPolicy> getPrivacyPolicyDetail(
            GetPrivacyPolicyDetailRequestDTO getPrivacyPolicyDetailRequestDTO
    ) throws CustomException {
        PrivacyPolicy privacyPolicy = getPrivacyPolicyValue(getPrivacyPolicyDetailRequestDTO.getPrivacyPolicyId());

        return ApiResponse.successWithResult(privacyPolicy);
    }

    @Override
    public ApiResponse<PrivacyPolicy> createPrivacyPolicy(
            CreatePrivacyPolicyRequestDTO createPrivacyPolicyRequestDTO
    ) throws CustomException {
        User user = getUserValue(TokenHelper.getPrincipal());

        PrivacyPolicy privacyPolicy = new PrivacyPolicy(
                createPrivacyPolicyRequestDTO.getQuestion(),
                createPrivacyPolicyRequestDTO.getAnswer(),
                user
        );

        privacyPolicyRepository.save(privacyPolicy);

        return ApiResponse.successWithResult(privacyPolicy);
    }

    @Override
    public ApiResponse<PrivacyPolicy> updatePrivacyPolicy(
            UpdatePrivacyPolicyRequestDTO updatePrivacyPolicyRequestDTO
    ) throws CustomException{

        PrivacyPolicy privacyPolicy = getPrivacyPolicyValue(updatePrivacyPolicyRequestDTO.getId());
        User user = getUserValue(TokenHelper.getPrincipal());

        privacyPolicy.setQuestion(updatePrivacyPolicyRequestDTO.getQuestion());
        privacyPolicy.setAnswer(updatePrivacyPolicyRequestDTO.getAnswer());
        privacyPolicy.setUpdatedBy(user);

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

    private User getUserValue(Long userId) throws CustomException{

        Optional<UserStatus> userStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (userStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        Optional<User> foundUser = userRepository.findByIdAndUserStatus(userId, userStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }
}
