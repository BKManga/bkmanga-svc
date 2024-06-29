package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreatePrivacyPolicyRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetPrivacyPolicyDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdatePrivacyPolicyRequestDTO;
import com.project.graduation.bkmangasvc.entity.PrivacyPolicy;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.PrivacyPolicyService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/privacyPolicy")
@CrossOrigin(origins = "*")
public class PrivacyPolicyController {
    private final PrivacyPolicyService privacyPolicyService;

    @GetMapping("/all")
    public ApiResponse<List<PrivacyPolicy>> getAllPrivacyPolicy() {
        return privacyPolicyService.getPrivacyPolicy();
    }

    @PostMapping(path = "/detail")
    public ApiResponse<PrivacyPolicy> getPrivacyPolicy(
            @Valid @RequestBody GetPrivacyPolicyDetailRequestDTO getPrivacyPolicyDetailRequestDTO
    ) throws CustomException {
        return privacyPolicyService.getPrivacyPolicyDetail(getPrivacyPolicyDetailRequestDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<PrivacyPolicy> createPrivacyPolicy(
        @Valid @RequestBody CreatePrivacyPolicyRequestDTO createPrivacyPolicyRequestDTO
    ) throws CustomException {
        return privacyPolicyService.createPrivacyPolicy(createPrivacyPolicyRequestDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    @Transactional(rollbackOn = {CustomException.class})
    public ApiResponse<PrivacyPolicy> updatePrivacyPolicy(
            @Valid @RequestBody UpdatePrivacyPolicyRequestDTO privacyPolicyEditRequestDTO
    ) throws CustomException {
        return privacyPolicyService.updatePrivacyPolicy(privacyPolicyEditRequestDTO);
    }
}
