package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.EditPrivacyPolicyRequestDTO;
import com.project.graduation.bkmangasvc.entity.PrivacyPolicy;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.PrivacyPolicyService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/edit")
    @Transactional(rollbackOn = {CustomException.class})
    public ApiResponse<PrivacyPolicy> editPrivacyPolicy(
            @Valid @RequestBody EditPrivacyPolicyRequestDTO privacyPolicyEditRequestDTO
    ) throws CustomException {
        return privacyPolicyService.editPrivacyPolicy(privacyPolicyEditRequestDTO);
    }
}
