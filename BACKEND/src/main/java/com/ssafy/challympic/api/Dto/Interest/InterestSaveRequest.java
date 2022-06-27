package com.ssafy.challympic.api.Dto.Interest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestSaveRequest {
    private int tag_no;

    @Builder
    public InterestSaveRequest(int tag_no) {
        this.tag_no = tag_no;
    }
}
