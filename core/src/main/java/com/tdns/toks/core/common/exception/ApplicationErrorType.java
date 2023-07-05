package com.tdns.toks.core.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 주의! error code 값을 변경할 때는 클라이언트에서 혹시 사용하고 있지 않은지 꼭 확인해야 합니다!
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum ApplicationErrorType {
    /**
     * Common Error Type
     */
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "try.again"),
    INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, "try.again"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "try.again"),
    COULDNT_FIND_ANY_DATA(HttpStatus.BAD_REQUEST, "try.again"),
    CLIENT_ABORT(HttpStatus.BAD_REQUEST, "try.again"),
    FROM_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "try.again"),
    INVALID_LOGIN_INFO(HttpStatus.BAD_REQUEST, "try.again"/*Invalid Login Info*/),
    TO_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "try.again"),

    /**
     * Auth or User Error Type
     */
    UNKNOWN_USER(HttpStatus.BAD_REQUEST, "try.again"),
    UNAUTHORIZED_USER(HttpStatus.BAD_REQUEST, "try.again"),
    NO_PROVIDER(HttpStatus.NO_CONTENT, "no provider"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "duplicated nickname"),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "authentication failed"),
    EMPTY_TOKEN(HttpStatus.BAD_REQUEST, "no token"),
    NO_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "error.no.authorization"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "error.invalid.access.token"),
    TOKEN_INTERNAL_ERROR(HttpStatus.UNAUTHORIZED, "토큰 verify 실패 (토큰 내부 값 오류)"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "error.invalid.refresh.token"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."),
    NOT_SET_NICKNAME(HttpStatus.NOT_FOUND, "닉네임을 설정하지 않았습니다"),

    /**
     * Quiz Error Type
     */
    ALREADY_LIKE_USER_QUIZ(HttpStatus.BAD_REQUEST, "error.already.liked"),
    ALREADY_SUBMITTED_USER_QUIZ(HttpStatus.BAD_REQUEST, "error.already.submitted"),
    ALREADY_FINISH_STUDY(HttpStatus.BAD_REQUEST, "error.invalid.already-finish-study"),
    OVER_MAX_HEADCOUNT(HttpStatus.BAD_REQUEST, "error.invalid.max-headcount"),
    ALREADY_JOIN_USER(HttpStatus.BAD_REQUEST, "error.invalid.already-join-user"),
    ALREADY_EXISTS_QUIZ_ROUND(HttpStatus.BAD_REQUEST, "퀴즈 라운드가 이미 존재합니다."),
    NOT_FOUND_QUIZ_ERROR(HttpStatus.NOT_FOUND, "해당 퀴즈가 존재하지 않습니다."),
    STILL_OPEN_LATEST_QUIZ(HttpStatus.CONFLICT, "마지막 퀴즈가 끝나지 않았습니다"),
    ;

    @Getter
    private HttpStatus httpStatus;

    @Getter
    private String message;

    public int getStatusCode() {
        return httpStatus.value();
    }
}
