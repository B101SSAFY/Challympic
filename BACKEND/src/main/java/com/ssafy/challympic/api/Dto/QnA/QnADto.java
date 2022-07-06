package com.ssafy.challympic.api.Dto.QnA;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class QnADto {
    private int qna_no;
    private String user_nickname;
    private String user_email;
    private String qna_title;
    private String qna_question;
    private String qna_answer;
    private LocalDateTime qna_question_regdate;
    private LocalDateTime qna_answer_regdate;
    private boolean isAnswer;

    @Builder
    public QnADto(int qna_no, String user_nickname, String user_email, String qna_title, String qna_question, String qna_answer, LocalDateTime qna_question_regdate, LocalDateTime qna_answer_regdate, boolean isAnswer) {
        this.qna_no = qna_no;
        this.user_nickname = user_nickname;
        this.user_email = user_email;
        this.qna_title = qna_title;
        this.qna_question = qna_question;
        this.qna_answer = qna_answer;
        this.qna_question_regdate = qna_question_regdate;
        this.qna_answer_regdate = qna_answer_regdate;
        this.isAnswer = isAnswer;
    }


    public QnADto update(boolean isAnswer) {
        this.isAnswer = isAnswer;
        return this;
    }
}
