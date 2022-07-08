package com.ssafy.challympic.api.Dto.Activity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ActivityResponse {
    List<TagDto> tagList;

    @Builder
    public ActivityResponse(List<TagDto> tagList) {
        this.tagList = tagList;
    }
}
