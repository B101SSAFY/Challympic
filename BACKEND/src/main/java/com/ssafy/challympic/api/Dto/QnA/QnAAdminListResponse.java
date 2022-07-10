package com.ssafy.challympic.api.Dto.QnA;

import com.ssafy.challympic.domain.QnA;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QnAAdminListResponse {

    private int qna_no;
    private String user_email;
    private String qna_title;
    private String qna_question;
    private String qna_answer;
    private LocalDateTime qna_question_regdate;
    private LocalDateTime qna_answer_regdate;
    private boolean isAnswer;

    @Builder
    public QnAAdminListResponse(QnA qnA) {
        this.qna_no = qnA.getNo();
        this.user_email = qnA.getUser().getEmail();
        this.qna_title = qnA.getTitle();
        this.qna_question = qnA.getQuestion();
        this.qna_answer = qnA.getAnswer();
        this.qna_question_regdate = qnA.getCreatedDate();
        this.qna_answer_regdate = qnA.getModifiedDate();
        if (this.qna_answer == null) this.isAnswer = false;
        else this.isAnswer = true;
    }
}
