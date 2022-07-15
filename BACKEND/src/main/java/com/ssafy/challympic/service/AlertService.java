package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Alert.AlertResponse;
import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    @Transactional
    public void saveAlert(Alert alert) {
        alertRepository.save(alert);
    }

    public List<AlertResponse> findAlertByUserNo(int userNo) {
        List<Alert> alerts = alertRepository.findAlertByUserNo(userNo);
        return alerts.stream()
                .map(a -> new AlertResponse(a.getUser().getNo(), a.getContent(), a.isConfirm(), a.getCreatedDate()))
                .collect(Collectors.toList());
    }
}
