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
     * common (-10000 ~ -19999)
     * 에러코드 재정의 필요 랜덤값 생성
     */
    INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, 10001, "Invalid Data Argument"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10002, "Internal Error"),
    COULDNT_FIND_ANY_DATA(HttpStatus.BAD_REQUEST, 10003, "couldn't find any data"),
    CLIENT_ABORT(HttpStatus.BAD_REQUEST, 10004, "Client Abort"),
    FROM_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10005, "From Json Error"),
    TO_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10006, "To Json Error"),

    /**
     * Auth or User Error Type (20001 ~ 29999)
     */
    UNKNOWN_USER(HttpStatus.BAD_REQUEST, 20001, "사용자 정보 없음"),
    UNAUTHORIZED_USER(HttpStatus.BAD_REQUEST, 20002, "인증되지 않은 사용자"),
    NO_PROVIDER(HttpStatus.NO_CONTENT, 20003, "no provider"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 20004, "엑세스 토큰 올바르지 않음"),
    INVALID_LOGIN_INFO(HttpStatus.BAD_REQUEST, 20005, "로그인 정보 올바르지 않음"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, 20006, "중복된 닉네임"),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, 20007, "인증 실패"),
    EMPTY_TOKEN(HttpStatus.BAD_REQUEST, 20008, "no token"),
    NO_AUTHORIZATION(HttpStatus.UNAUTHORIZED, 20009, "error.no.authorization"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 20010, "리프레시 토큰 올바르지 않음"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 20011, "사용자 정보를 찾을 수 없습니다."),

    /**
     * Quiz Error Type (30001 ~ 39999)
     */
    ALREADY_LIKE_USER_QUIZ(HttpStatus.BAD_REQUEST, 30001, "이미 좋아요를 했습니다."),
    ALREADY_SUBMITTED_USER_QUIZ(HttpStatus.BAD_REQUEST, 30002, "이미 제출한 퀴즈입니다."),
    ALREADY_EXISTS_QUIZ_ROUND(HttpStatus.BAD_REQUEST, 30003, "퀴즈 라운드가 이미 존재합니다."),
    NOT_FOUND_QUIZ_ERROR(HttpStatus.NOT_FOUND, 30004, "해당 퀴즈가 존재하지 않습니다."),
    STILL_OPEN_LATEST_QUIZ(HttpStatus.CONFLICT, 30005, "마지막 퀴즈가 끝나지 않았습니다"),
    NOT_FOUND_QUIZ_REPLY(HttpStatus.NOT_FOUND, 30006, "퀴즈 답변을 찾을 수 없습니다"),


    /**
     * Study Error Type (40001 ~ 49999)
     **/
    NOT_FOUND_STUDY(HttpStatus.NOT_FOUND, 40001, "스터디를 찾을 수 없습니다."),
    ALREADY_FINISH_STUDY(HttpStatus.BAD_REQUEST, 40002, "기간이 종료된 스터디입니다."),
    CHECK_START_END_DATE(HttpStatus.BAD_REQUEST, 40003, "스터디 종료 시간이 시작시간보다 빠릅니다."),
    OVER_MAX_HEADCOUNT(HttpStatus.BAD_REQUEST, 40004, "참가 인원을 초과했습니다."),
    ALREADY_JOIN_USER(HttpStatus.BAD_REQUEST, 40005, "이미 참여한 사용자입니다."),

    ;

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
