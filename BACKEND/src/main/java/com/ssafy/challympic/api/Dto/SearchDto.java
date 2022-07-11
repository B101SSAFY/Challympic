package com.ssafy.challympic.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data // TODO : 어노테이션 수정
@AllArgsConstructor
public class SearchDto {
    private int search_no;
    private int user_no;
    private int tag_no;
    private String tag_content;
    private String search_content;
    private LocalDateTime search_regdate;
    
    // TODO : builder 추가
}
