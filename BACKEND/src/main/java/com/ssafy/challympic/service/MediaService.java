package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Media;
import com.ssafy.challympic.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MediaService {

    private final MediaRepository mediaRepository;

    public Media getMedia(int fileNo){
        Media media = mediaRepository.findById(fileNo) // TODO: 선언 없이 바로 return
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
        return media;
    }

    @Transactional
    public int saveMedia(Media media) {
        // 중복 확인
        return mediaRepository.save(media).getNo();
    }

    @Transactional
    public void delete(int fileNo){
        Media media = mediaRepository.findById(fileNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
        mediaRepository.delete(media);
    }

}
