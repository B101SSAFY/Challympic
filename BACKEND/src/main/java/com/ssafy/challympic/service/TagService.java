package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.PostTag;
import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.repository.PostTagRepository;
import com.ssafy.challympic.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

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

    /**
     * 태그 선택
     */
    public Tag findOne(int tag_no){
        return tagRepository.findById(tag_no).orElseThrow(() -> new NoSuchElementException("존재하지 않는 태그입니다."));
    }

    public Tag findTagByTagContent(String tagContent) {
        Tag tag = tagRepository.findByContent(tagContent); // TODO : 선언 없이 바로 return하도록 수정
        return tag;
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

    public List<Tag> findAllTagList() {
        return tagRepository.findAll();
    }

    public List<Tag> findRecentAllTagList() {
        return tagRepository.findAllOrderByNoDesc();
    }

}
