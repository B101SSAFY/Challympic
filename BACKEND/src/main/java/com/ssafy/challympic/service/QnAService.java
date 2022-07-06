package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.QnA.QnADto;
import com.ssafy.challympic.api.Dto.QnA.QnARequest;
import com.ssafy.challympic.domain.QnA;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnARepository;

    public QnA findOne(int qna_no){ return qnARepository.findById(qna_no).orElseThrow(() -> new NoSuchElementException("존재하지 않는 QnA입니다.")); }

    @Transactional
    public void save(QnARequest request, User user){
        QnA qnA = QnA.builder()
                .user(user)
                .title(request.getQna_title())
                .question(request.getQna_question())
                .build();
        qnARepository.save(qnA);
    }

    public List<QnADto> findByUser(int user_no){
        List<QnA> qnAList = qnARepository.findByUser_No(user_no);
        return qnAList.stream()
                .map(q ->
                        QnADto.builder()
                                .qna_no(q.getNo())
                                .user_nickname(q.getUser().getNickname())
                                .qna_title(q.getTitle())
                                .qna_question(q.getQuestion())
                                .qna_answer(q.getAnswer())
                                .qna_question_regdate(q.getCreatedDate())
                                .qna_answer_regdate(q.getModifiedDate())
                                .build()
                        )
                .collect(Collectors.toList());
    }
}
