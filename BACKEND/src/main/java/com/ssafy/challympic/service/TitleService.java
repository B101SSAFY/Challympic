package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Title;
import com.ssafy.challympic.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    @Transactional
    public void saveTitles(Title title) {
        titleRepository.save(title);
    } // TODO: 사용하지 않음. title 어떻게 사용하고 있는지 확인 필요

    public List<Title> findTitlesByUserNo(int user_no) {return titleRepository.findByUser_No(user_no);}

}
