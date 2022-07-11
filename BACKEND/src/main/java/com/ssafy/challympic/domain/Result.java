package com.ssafy.challympic.domain;

// TODO: 이게 도메인에 있어도 될지... DTO쪽으로 가는게 맞을까요...?
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private boolean isSuccess;
    private int code;
    private T data;

    public Result(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }
}
