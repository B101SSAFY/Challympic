package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Challenge.ChallengeInfoResponse;
import com.ssafy.challympic.api.Dto.Challenge.ChallengeResponse;
import com.ssafy.challympic.api.Dto.Challenge.challengeTitleCheckRequest;
import com.ssafy.challympic.api.Dto.Challenge.CreateChallengeRequest;
import com.ssafy.challympic.api.Dto.Subscription.AddSubscriptionResponse;
import com.ssafy.challympic.api.Dto.User.ChallengerDto;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengerRepository challengerRepository;
    private final ChallengeTagRepository challengeTagRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final TagService tagService;
    private final TitleRepository titleRepository;

    public List<Challenger> getChallengerByChallengeNo(int challengeNo){
        return challengerRepository.findByChallengeNo(challengeNo);
    }

    public List<Challenge> findAllChallenge() {
        return challengeRepository.findAll();
    }

    @Transactional
    public Challenge saveChallenge(CreateChallengeRequest request) {
        // 권한 설정
        ChallengeAccess access;
        List<Integer> challengers = new ArrayList<>();
        if(request.getChallengers().size() == 0) access = ChallengeAccess.PUBLIC;
        else {
            access = ChallengeAccess.PRIVATE;
            for(String str : request.getChallengers().subList(1, request.getChallengers().size())) {
                User challenger = userRepository.findByNickname(str).orElseThrow(() ->
                    new NoSuchElementException("존재하지 않는 사용자입니다.")
                );
                challengers.add(challenger.getNo());
            }
        }

        // Title 저장
        Title title = Title.builder()
                .name(request.getTitle_name())
                .build();

        User user = userRepository.findById(request.getUser_no())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        Challenge challenge = Challenge.builder()
                .user(user)
                .end(request.getChallenge_end())
                .access(access)
                .type(request.getChallenge_type())
                .title(request.getChallenge_title())
                .content(request.getChallenge_content())
                .build();

        // 중복 확인
        if(validateDuplicateChallenge(challenge)) {
            throw new IllegalStateException("이미 존재하는 챌린지입니다.");
        }
        // 챌린지 저장
        challengeRepository.save(challenge);

        // 챌린저 저장
        for(int cr : challengers) {
            User curUser = userRepository.findById(cr).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
            Challenger challenger = Challenger.builder()
                    .challenge(challenge)
                    .user(curUser)
                    .build();
            challengerRepository.save(challenger);

            // 태그 된 사람에게 알림
            Alert alert = Alert.builder()
                    .user(curUser)
                    .content(user.getNickname() + "님이 챌린지에 초대했습니다.")
                    .build();
            alertRepository.save(alert);
        }

        // 내용 파싱해서 태그 저장
        String content = request.getChallenge_content();
        List<String> tagContentList = new ArrayList<>();
        StringBuilder sb = null;

        // 챌린지 제목 태그로 저장
        tagContentList.add(request.getChallenge_title());
        String tagTitle = request.getChallenge_title();
        tagService.saveTag(tagTitle, true);

        // 챌린지 내용 태그 파싱 후 저장
        for(char c : content.toCharArray()) {
            if(c == '#') {
                if(sb != null) {
                    tagContentList.add(sb.toString());
                    tagService.saveTag(sb.toString());
                }
                sb = new StringBuilder();
            }

            if(c == ' ' && sb != null) {
                tagService.saveTag(sb.toString());
                tagContentList.add(sb.toString());
                sb = null;
            }

            if(sb == null) continue;
            sb.append(c);
        }

        if(sb != null) {
            tagService.saveTag(sb.toString());
            tagContentList.add(sb.toString());
        }

        // 챌린지 태그로 저장
        for(String s : tagContentList) {
            ChallengeTag challengeTag = ChallengeTag.builder()
                    .tag(tagService.findTagByTagContent(s))
                    .challenge(challenge)
                    .build();
            challengeTagRepository.save(challengeTag);
        }

        // 타이틀 등록
        title.update(challenge);
        titleRepository.save(title);

        return challenge;
    }

    private boolean validateDuplicateChallenge(Challenge challenge) {
        List<Challenge> findChallenges = challengeRepository.findByTitleOrderByEndDesc(challenge.getTitle());
        if(findChallenges.isEmpty()) return true;
        for(Challenge c : findChallenges) {
            if(c.getEnd().after(new Date())){
                return false;
            }
        }
        return true;
    }

    public boolean validateDuplicateTitle(challengeTitleCheckRequest request) {
        List<Challenge> challenges = challengeRepository.findByTitle(request.getChallenge_title());

        if(challenges.size() == 0){
            return true;
        }

        for(Challenge c : challenges) {
            if(c.getEnd().after(new Date())) return false;
        }

        return true;
    }

    @Transactional
    public List<AddSubscriptionResponse> addSubscription(int challengeNo, int userNo) {
        Challenge challenge = challengeRepository.findById(challengeNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 챌린지입니다."));
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        Subscription subscription = subscriptionRepository.findByChallengeNoAndUser_No(challenge.getNo(), user.getNo())
                .orElse(null);
        if (subscription == null) {
            subscriptionRepository.save(Subscription.builder()
                    .challenge(challenge)
                    .user(user)
                    .build());
        } else {
            subscriptionRepository.delete(subscription);
        }
        return getSubscriptionDtoList(userNo);
    }

    private List<AddSubscriptionResponse> getSubscriptionDtoList(int userNo) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByUserNo(userNo);
        List<AddSubscriptionResponse> subscriptionDtoList = new ArrayList<>();
        if(!subscriptionList.isEmpty()) {
            subscriptionDtoList = subscriptionList.stream()
                    .map(AddSubscriptionResponse::new)
                    .collect(Collectors.toList());
        }
        return subscriptionDtoList;
    }

    public Challenge findChallengeByChallengeNo(int challengeNo) {
        return challengeRepository.findById(challengeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 챌린지가 없습니다."));
    }
    public int challengeReportCntByUser(int user_no){
        List<Challenge> findChallengeList = challengeRepository.findByUser_No(user_no);
        int reportCnt = 0;
        for (Challenge challenge : findChallengeList) {
            reportCnt += challenge.getReport();
        }
        return reportCnt;
    }

    public List<Challenge> findChallengesByTag(String tag_content) {
        List<ChallengeTag> challengeTags = challengeTagRepository.findByTag_Content(tag_content);
        return challengeTags.stream()
                .map(ChallengeTag::getChallenge)
                .collect(Collectors.toList());
    }

    public List<ChallengeResponse> getChallenges() {
        return challengeRepository.findAll().stream()
                .map(ChallengeResponse::new)
                .collect(Collectors.toList());
    }

    public ChallengeInfoResponse getChallengeInfo(int challengeNo) {
        Challenge challenge = challengeRepository.findById(challengeNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 챌린지입니다."));
        List<Challenger> challengerList = challengerRepository.findByChallengeNo(challengeNo);
        List<ChallengerDto> challengers = new ArrayList<>();
        challengers = challengerList.stream()
                .map(cs -> {
                    User user = userRepository.findById(cs.getUser().getNo())
                            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
                    return new ChallengerDto(user);
                })
                .collect(Collectors.toList());
        return new ChallengeInfoResponse(challenge, challengers);
    }
}
