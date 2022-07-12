package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Challenge.ChallengeResponseDto;
import com.ssafy.challympic.api.Dto.Challenge.ChallengeTitleCheckRequsetDto;
import com.ssafy.challympic.api.Dto.Challenge.CreateChallengeRequset;
import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.SubscriptionDto;
import com.ssafy.challympic.api.Dto.UserDto;
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
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final TagService tagService;
    private final TitleRepository titleRepository;

    public List<Challenge> getChallengeByUserNo(int userNo) {
        return challengeRepository.findByUser_No(userNo);
    }

    public List<Challenger> getChallengerByChallengeNo(int challengeNo){
        return challengerRepository.findByChallengeNo(challengeNo);
    }

    public List<Challenge> findAllChallenge() {
        return challengeRepository.findAll();
    }

    @Transactional
    public Challenge saveChallenge(CreateChallengeRequset request) {
        // 권한 설정
        ChallengeAccess access;
        List<Integer> challengers = new ArrayList<>();
        if(request.getChallengers().size() == 0) access = ChallengeAccess.PUBLIC;
        else {
            access = ChallengeAccess.PRIVATE;
            // TODO : str 대신 user_nickname으로 받고 바로 사용하도록 수정 가능
            for(String str : request.getChallengers().subList(1, request.getChallengers().size())) {
                String user_nickname = str;
                User challenger = userRepository.findByNickname(user_nickname).orElseThrow(() ->
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
        // TODO : validation 확인 안되어있음.
        validateDuplicateChallenge(challenge);
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
        String tagTitle = "#" + request.getChallenge_title();
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

    // TODO: void -> boolean, Error Throw보단 boolean으로 return할 수 있도록
    private void validateDuplicateChallenge(Challenge challenge) {
        List<Challenge> findChallenges = challengeRepository.findByTitleOrderByEndDesc(challenge.getTitle());
        for(Challenge c : findChallenges) {
            if(c.getEnd().after(new Date())){
                throw new IllegalStateException("이미 존재하는 챌린지입니다.");
            }
        }
    }

    // TODO : void -> boolean, Error Throw보단 boolean으로 return할 수 있도록
    public void validateDuplicateTitle(ChallengeTitleCheckRequsetDto request) {
        List<Challenge> challenges = challengeRepository.findByTitle(request.getChallenge_title()); // TODO : 확인필요 - challenges == null로 나옴.

        if(challenges.size() != 0){
            throw new NoSuchElementException("챌린지가 없습니다.");
        }

        for(Challenge c : challenges) {
            if(c.getEnd().after(new Date())) throw new NoSuchElementException("진행중인 챌린지가 없습니다.");
        }
    }

    @Transactional
    public List<SubscriptionDto> addSubscription(int challengeNo, int userNo) {
        Challenge challenge = challengeRepository.findById(challengeNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 챌린지입니다."));
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        try {
            Subscription subscription = subscriptionRepository.findByChallengeNoAndUser_No(challenge.getNo(), user.getNo()).get(); // TODO : orElseThrow로 변경
            subscriptionRepository.delete(subscription);
        }catch (NoSuchElementException e) {
            subscriptionRepository.save(Subscription.builder()
                    .challenge(challenge)
                    .user(user)
                    .build());
        }
        return getSubscriptionDtoList(userNo);
    }

    private List<SubscriptionDto> getSubscriptionDtoList(int userNo) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByUserNo(userNo);
        List<SubscriptionDto> subscriptionDtoList = new ArrayList<>();
        if(!subscriptionList.isEmpty()) {
            subscriptionDtoList = subscriptionList.stream()
                    .map(s -> new SubscriptionDto(s)) // TODO : 생성자 builder로 수정
                    .collect(Collectors.toList());
        }
        return subscriptionDtoList;
    }


    public List<Challenge> getChallengeBySubscription(int userNo) {
        return challengeRepository.findByUserNoFromSubscription(userNo);
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

    public int findSubscriptionCnt(int challenge_no) {
        return subscriptionRepository.findAllByChallengeNo(challenge_no).size();
    }

    public int findPostCnt(int challenge_no) {
        return postRepository.findByChallengeNo(challenge_no).size();
    }

    public List<Challenge> findChallengesByTag(String tag_content) {
        return challengeRepository.findByTagContent(tag_content);
    }

    public List<ChallengeResponseDto> getChallenges() {
        return challengeRepository.findAll().stream()
                .map(c -> new ChallengeResponseDto(c)) // TODO : 생성자 builder로 수정
                .collect(Collectors.toList());
    }

    public ChallengeDto getChallengeInfo(int challengeNo) {
        Challenge challenge = challengeRepository.findById(challengeNo)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 챌린지입니다."));
        List<Challenger> challengerList = challengerRepository.findByChallengeNo(challengeNo);
        List<UserDto> challengers = new ArrayList<>();
        challengers = challengerList.stream()
                .map(cs -> {
                    User user = userRepository.findById(cs.getUser().getNo())
                            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
                    return new UserDto(user);
                })
                .collect(Collectors.toList());
        return new ChallengeDto(challenge, challengers);
    }


}
