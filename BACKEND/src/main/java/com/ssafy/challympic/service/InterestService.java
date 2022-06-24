package com.ssafy.challympic.service;

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
    public void save(int userNo, int tagNo){

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NoSuchElementException());
        Tag tag = tagRepository.findById(tagNo)
                .orElseThrow(() -> new NoSuchElementException());

        Interest interest = Interest.builder()
                .user(user)
                .tag(tag)
                .build();

        interestRepository.save(interest);
    }

    public List<Interest> findByUser(int userNo){
        return interestRepository.findAllByUser(userNo);
    }

    @Transactional
    public void delete(int userNo, int tagNo){
        Interest interest = interestRepository.findByUserAndTag(userNo, tagNo)
                        .orElseThrow(() -> new NoSuchElementException());
        interestRepository.delete(interest);
    }
}
