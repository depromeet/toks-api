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
    INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, "invalid data argument"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다. 잠시만 기달려주세요."),
    COULDNT_FIND_ANY_DATA(HttpStatus.BAD_REQUEST, "try.again"), //?????
    FROM_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "json parsing error"),
    INVALID_LOGIN_INFO(HttpStatus.BAD_REQUEST, "invalid login info"),
    TO_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "json parsing error"),

    /**
     * Auth or User Error Type
     */
    UNKNOWN_USER(HttpStatus.BAD_REQUEST, "try.again"),
    NO_PROVIDER(HttpStatus.NO_CONTENT, "no provider"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "duplicated nickname"),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "authentication failed"),
    TOKEN_INTERNAL_ERROR(HttpStatus.UNAUTHORIZED, "토큰 verify 실패 (토큰 내부 값 오류)"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "error.invalid.refresh.token"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."),
    NOT_SET_NICKNAME(HttpStatus.NOT_FOUND, "닉네임을 설정하지 않았습니다"),
    NOT_AUTHORIZED_ADMIN_USER(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    /**
     * Quiz Error Type
     */
    ALREADY_SUBMITTED_USER_QUIZ(HttpStatus.BAD_REQUEST, "이미 진행한 퀴즈입니다."),
    NOT_FOUND_QUIZ_ERROR(HttpStatus.NOT_FOUND, "해당 퀴즈가 존재하지 않습니다."),
    INVALID_QUIZ_SEARCH_ERROR(HttpStatus.BAD_REQUEST, "퀴즈 조회 요청 정보가 잘못되었습니다."),

    /**
     * Category Error Type
     */
    NOT_FOUND_CATEGORY_ERROR(HttpStatus.NOT_FOUND, "카테고리 데이터를 찾을 수 없습니다."),
    NOT_SET_USER_CATEGORY(HttpStatus.NOT_FOUND, "카테고리 설정 하지 않은 사옹자입니다."),

    /**
     * UserActivityCount Error Type
     */
    NOT_FOUND_USER_ACTIVITY(HttpStatus.NOT_FOUND, "사용자의 활동 기록을 찾을 수 없습니다."),

    /**
     * BottomBanner Error Type
     */
    NOT_FOUND_BOTTOM_BANNER_ERROR(HttpStatus.NOT_FOUND, "바텀 배너 정보를 찾을 수 없습니다."),
    ;

    @Getter
    private HttpStatus httpStatus;

    @Getter
    private String message;

    public int getStatusCode() {
        return httpStatus.value();
    }
}
