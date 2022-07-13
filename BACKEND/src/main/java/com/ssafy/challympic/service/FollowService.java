package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.User.FollowListResponse;
import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.domain.Follow;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.AlertRepository;
import com.ssafy.challympic.repository.FollowRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AlertRepository alertRepository;

    @Transactional
    public boolean follow(int srcNo, int destNo){
        try{ // TODO : 로직 다시 한 번 확인해야함.
            // get은 null일 경우 NoSuchElementException 반환
            Follow follow = followRepository.findBySrcUser_NoAndDestUser_No(srcNo, destNo).get(); // TODO : get 말고 orElseThrow로 수정
            followRepository.delete(follow);
            return false;
        }catch (NoSuchElementException e){
            User srcUser = userRepository.findById(srcNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));
            User destUser = userRepository.findById(destNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));
            Follow newFollow = Follow.builder()
                    .src_no(srcUser)
                    .dest_no(destUser)
                    .build();
            followRepository.save(newFollow);

            // 팔로우했을때 알림
            User writer = userRepository.findById(destNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            User follower = userRepository.findById(srcNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            Alert alert = Alert.builder()
                    .user(writer)
                    .content(follower.getNickname() + "님이 팔로우합니다.")
                    .build();
            alertRepository.save(alert);

            return true;
        }
    }

    /**
     * userNo가 팔로우 한 사람들
     * @param userNo
     * @return
     */
    public List<FollowListResponse> following(int userNo, int loginUser){
        List<Follow> following = followRepository.findBySrcUser_No(userNo);
        return following.stream()
                .map(f ->{
                    User user = userRepository.findById(f.getDestUser().getNo())
                            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));
                    boolean isFollow = followRepository.findBySrcUser_NoAndDestUser_No(loginUser, f.getDestUser().getNo()).isEmpty();
                    return new FollowListResponse(user, !isFollow);
                })
                .collect(Collectors.toList());
    }

    /**
     * userNo를 팔로우 한 사람들
     * @param userNo
     * @return
     */
    public List<FollowListResponse> follower(int userNo, int loginUser){
        List<Follow> follower = followRepository.findByDestUser_No(userNo);
        return follower.stream()
                .map(f ->{
                    User user = userRepository.findById(f.getSrcUser().getNo())
                            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));
                    boolean isFollow = followRepository.findBySrcUser_NoAndDestUser_No(loginUser, f.getSrcUser().getNo()).isEmpty();
                    return new FollowListResponse(user, !isFollow);
                })
                .collect(Collectors.toList());
    }

    public boolean isFollow(int srcNo, int destNo) {
        return followRepository.findBySrcUser_NoAndDestUser_No(srcNo, destNo).isPresent();
    }

    public int followingCnt(int userNo) {
        return followRepository.countBySrcUser_No(userNo);
    }

    public int followerCnt(int userNo) {
        return followRepository.countByDestUser_No(userNo);
    }

}

