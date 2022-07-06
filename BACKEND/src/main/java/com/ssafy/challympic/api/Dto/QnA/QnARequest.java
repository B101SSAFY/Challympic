package com.ssafy.challympic.api.Dto.QnA;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QnARequest {
    private int qna_no;
    private String qna_answer;
    private String qna_title;
    private String qna_question;

    @Builder
    public QnARequest(int qna_no, String qna_answer, String qna_title, String qna_question) {
        this.qna_no = qna_no;
        this.qna_answer = qna_answer;
        this.qna_title = qna_title;
        this.qna_question = qna_question;
    }
}
