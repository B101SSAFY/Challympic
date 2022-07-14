package com.ssafy.challympic.api.Dto.Search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRecentResponse {
    private int search_no;
    private int user_no;
    private int tag_no;
    private String tag_content;
    private String search_content;
    private LocalDateTime search_regdate;
}
