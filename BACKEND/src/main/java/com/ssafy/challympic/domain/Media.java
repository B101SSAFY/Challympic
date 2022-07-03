package com.ssafy.challympic.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Media {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    // 원래 파일명
    private String file_name;

    // 파일 저장 경로
    private String file_path;

    // 저장된 파일명
    private String file_savedname;

    @Builder
    public Media(String file_name, String file_savedname, String file_path){
        this.file_name = file_name;
        this.file_savedname = file_savedname;
        this.file_path = file_path;
    }
}

