package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Interest.InterestListResponse;
import com.ssafy.challympic.domain.Interest;
import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.InterestRepository;
import com.ssafy.challympic.repository.TagRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    /**
     * 관심 저장
     */
    @Transactional
    public int save(int userNo, int tagNo){

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NoSuchElementException());
        Tag tag = tagRepository.findById(tagNo)
                .orElseThrow(() -> new NoSuchElementException());

        Interest interest = Interest.builder()
                .user(user)
                .tag(tag)
                .build();

        return interestRepository.save(interest).getNo();
    }

    public List<InterestListResponse> findByUser(int userNo){

        List<Interest> allByUser_no = interestRepository.findAllByUser_No(userNo);
        List<InterestListResponse> interests = allByUser_no.stream()
                .map(i -> new InterestListResponse(i))
                .collect(Collectors.toList());

        return interests;
    }

    @Transactional
    public void delete(int userNo, int tagNo){
        Interest interest = interestRepository.findByUser_NoAndTag_No(userNo, tagNo)
                        .orElseThrow(() -> new NoSuchElementException());
        interestRepository.delete(interest);
    }
}
