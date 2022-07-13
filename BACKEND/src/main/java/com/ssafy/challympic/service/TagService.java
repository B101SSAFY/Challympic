package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.PostTag;
import com.ssafy.challympic.domain.Search;
import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.repository.PostTagRepository;
import com.ssafy.challympic.repository.SearchRepository;
import com.ssafy.challympic.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final SearchRepository searchRepository;

    /**
     * 태그 저장
     */
    @Transactional
    public void saveTag(String tag_content){
        Tag isTag = tagRepository.findByContent(tag_content);
        if(isTag != null) return;
        Tag tag = Tag.builder()
                .content(tag_content)
                .build();
        tagRepository.save(tag);
    }

    /**
     * 챌린지 제목 태그로 저장
     */
    @Transactional
    public void saveTag(String challenge_title, boolean isTitle){
        Tag isTag = tagRepository.findByContent(challenge_title);
        if(isTag != null) return;
        Tag tag = Tag.builder()
                .content(challenge_title)
                .isChallenge(isTitle?"challenge":null)
                .build();
        tagRepository.save(tag);
    }

    public Tag findTagByTagContent(String tagContent) {
        return tagRepository.findByContent(tagContent);
    }

    // TODO: Transaction 추가
    public void savePostTag(PostTag postTag) {
        postTagRepository.save(postTag);
    }

    // TODO: Transaction 추가
    public void deletePostTag(PostTag postTag){ postTagRepository.delete(postTag);}

    public List<PostTag> findPostTagList(int post_no) {
        return postTagRepository.findAllByPostNo(post_no);
    }

    public List<Tag> findRecentAllTagList() {
        return tagRepository.findAllOrderByNoDesc();
    }

    public List<Tag> getTagsVer01(int userNo) {
        List<Search> searchList = searchRepository.findAllByUserNo(userNo);
        List<Tag> tagAll = new ArrayList<>();
        for(Search search : searchList) {
            Tag searchTag = tagRepository.findByContent(search.getTag_content());
            if(searchTag.getIsChallenge()  == null) tagAll.add(searchTag);
        }

        List<Tag> tempTagList = tagRepository.findAll();
        int maxTag = tempTagList.size();
        int[][] tagCount = new int[maxTag][2];

        for(int i = 0; i < maxTag; i++) tagCount[i][0] = i + 1;
        for(Tag tag : tagAll) {
            tagCount[tag.getNo() - 1][1]++;
        }

        Arrays.sort(tagCount, (int[] o1, int[] o2) -> o2[1] - o1[1]);

        List<Tag> tagResponse = new ArrayList<>();
        for(int i = 0; i < Math.min(maxTag, 5); i++) {
            tagResponse.add(tagRepository.findById(tagCount[i][0]).orElseThrow(() -> new NoSuchElementException("존재하지 않는 태그입니다.")));
        }
        return tagResponse;
    }
}
