package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.domain.SearchChallenge;
import com.ssafy.challympic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final TagRepository tagRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SearchChallengeRepository searchChallengeRepository;

    public List<Tag> findTagList() {
        List<Tag> tagList = tagRepository.findAll();
        tagList.removeIf(t -> t.getIsChallenge() != null);
        return tagList;
    }

    public List<User> findUserList(){
        return userRepository.findAll();
    }

    public List<Search> findTagListByUserNo(int userNo) {
        return searchRepository.findByUserNo(userNo);
    }

    public List<Challenge> findChallengeListByTagContent(String tag) {
        return challengeRepository.findByTagContent(tag);
    }

    public List<Post> findPostListByTagContent(String tag) {
        return postRepository.findFromPostTagByTagContent(tag);
    }

    public List<Challenge> findTrendChallenge() {
        List<Challenge> searchedChallenges = challengeRepository.findFromSearchChallenge();
        List<Challenge> allChallenge = challengeRepository.findAll();
        int challengeSize = allChallenge.size();
        List<int[]> challengeCount = new ArrayList<>();

        for(Challenge c : searchedChallenges) {
            boolean isFind = false;
            for(int[] count : challengeCount) {
                if(count[0] == c.getNo()) {
                    count[1]++;
                    isFind = true;
                }
            }
            if(!isFind) {
                challengeCount.add(new int[]{c.getNo(), 0});
            }
        }

        challengeCount.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });

        List<Challenge> trendChallenge = new ArrayList<>();
        System.out.println(challengeSize);
        for(int i = 0; i <= (Math.min(challengeSize-1, 4)); i++) {
            int challengeNo = challengeCount.get(i)[0];
            trendChallenge.add(challengeRepository
                    .findById(challengeNo).get());
        }

        return trendChallenge;
    }

    public List<User> findRank() {
        return userRepository.findRank();
    }

    @Transactional
    public void saveSearchRecord(String search_content, User user) {
        Tag tag = tagRepository.findByTagContent(search_content);
        Search search = new Search();
        search.setSearch_content(search_content);
        search.setUser(user);
        if(tag != null) {
            search.setTag_no(tag.getNo());
            search.setTag_content(tag.getContent());
        }
        searchRepository.save(search);
    }

    @Transactional
    public void saveSearchChallenge(SearchChallenge searchChallenge){
        searchChallengeRepository.save(searchChallenge);
    }
}
