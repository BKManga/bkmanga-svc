package com.project.graduation.bkmangasvc.dto.response;

import com.project.graduation.bkmangasvc.model.HistoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetListHistoryResponseDTO {
    private List<HistoryResponse> historyResponseList;
}
