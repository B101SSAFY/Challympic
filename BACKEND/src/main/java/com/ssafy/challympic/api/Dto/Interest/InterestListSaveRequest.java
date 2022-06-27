package com.ssafy.challympic.api.Dto.Interest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InterestListSaveRequest {
    private String user_email;
    private List<Integer> interests;

    @Builder
    public InterestListSaveRequest(String user_email, List<Integer> interests) {
        this.user_email = user_email;
        this.interests = interests;
    }
}
