package com.tdns.toks.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 주의! error code 값을 변경할 때는 클라이언트에서 혹시 사용하고 있지 않은지 꼭 확인해야 합니다!
 */
@AllArgsConstructor
public enum ApplicationErrorType {
    /**
     * common (-10000 ~ -19999)
     * 에러코드 재정의 필요 랜덤값 생성
     */
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, -10000, "try.again"),
    INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, -10001, "try.again"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10002, "try.again"),
    COULDNT_FIND_ANY_DATA(HttpStatus.BAD_REQUEST, -40001, "try.again"),
    CLIENT_ABORT(HttpStatus.BAD_REQUEST, -10005, "try.again"),
    FROM_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -40014, "try.again"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, -20027, "error.invalid.access.token"),
    INVALID_LOGIN_INFO(HttpStatus.BAD_REQUEST, -20007, "try.again"/*Invalid Login Info*/),
    TO_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -40015, "try.again"),
    UNKNOWN_USER(HttpStatus.BAD_REQUEST, -10006, "try.again"),
    UNAUTHORIZED_USER(HttpStatus.BAD_REQUEST, -10006, "try.again"),
    ALREADY_JOIN_USER(HttpStatus.BAD_REQUEST, -10008, "이미 해당 스터디에 가입한 유저입니다."),
    ALREADY_FINISH_STUDY(HttpStatus.BAD_REQUEST, -10009, "이미 끝난 스터디에는 가입할 수 없습니다."),
    OVER_MAX_HEADCOUNT(HttpStatus.BAD_REQUEST, -10010, "가입 인원이 초과된 스터디입니다");
    @Getter
    private HttpStatus httpStatus;

    @Getter
    private Integer code;

    @Getter
    private String message;

    public int getStatusCode() {
        return httpStatus.value();
    }
}
