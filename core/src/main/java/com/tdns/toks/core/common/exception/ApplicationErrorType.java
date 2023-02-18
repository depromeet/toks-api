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
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, -10000, "try.again"),
    INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, -10001, "try.again"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10002, "try.again"),
    COULDNT_FIND_ANY_DATA(HttpStatus.BAD_REQUEST, -40001, "try.again"),
    CLIENT_ABORT(HttpStatus.BAD_REQUEST, -10005, "try.again"),
    FROM_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -40014, "try.again"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, -20027, "error.invalid.access.token"),
    INVALID_LOGIN_INFO(HttpStatus.BAD_REQUEST, -20007, "try.again"/*Invalid Login Info*/),
    TO_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -40015, "try.again"),
    CONVERT_STRING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10100, "String 변환 오류"),
    CONVERT_ARRAY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10101, "List 변환 오류"),
    NO_IMAGE(HttpStatus.BAD_REQUEST, 10102, "전달된 이미지 없음"),

    /**
     * Auth or User Error Type
     */
    UNKNOWN_USER(HttpStatus.BAD_REQUEST, -10006, "try.again"),
    UNAUTHORIZED_USER(HttpStatus.BAD_REQUEST, -10006, "try.again"),
    NO_PROVIDER(HttpStatus.NO_CONTENT, -20010, "no provider"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, -20011, "duplicated nickname"),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, -20012, "authentication failed"),
    EMPTY_TOKEN(HttpStatus.BAD_REQUEST, -20013, "no token"),
    NO_AUTHORIZATION(HttpStatus.UNAUTHORIZED, -20017, "error.no.authorization"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, -20014, "error.invalid.refresh.token"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, -20020, "사용자 정보를 찾을 수 없습니다."),

    /**
     * Quiz Error Type
     */
    ALREADY_LIKE_USER_QUIZ(HttpStatus.BAD_REQUEST, -20015, "error.already.liked"),
    ALREADY_SUBMITTED_USER_QUIZ(HttpStatus.BAD_REQUEST, -20016, "error.already.submitted"),
    ALREADY_FINISH_STUDY(HttpStatus.BAD_REQUEST, -20017, "error.invalid.already-finish-study"),
    OVER_MAX_HEADCOUNT(HttpStatus.BAD_REQUEST, -20018, "error.invalid.max-headcount"),
    ALREADY_JOIN_USER(HttpStatus.BAD_REQUEST, -20019, "error.invalid.already-join-user"),
    ALREADY_EXISTS_QUIZ_ROUND(HttpStatus.BAD_REQUEST, -20020, "퀴즈 라운드가 이미 존재합니다."),
    NOT_FOUND_QUIZ_ERROR(HttpStatus.NOT_FOUND, -20021, "해당 퀴즈가 존재하지 않습니다."),
    STILL_OPEN_LATEST_QUIZ(HttpStatus.CONFLICT, -20022, "마지막 퀴즈가 끝나지 않았습니다"),
    EXCEEDED_IMAGE_UPLOAD_LIMIT(HttpStatus.BAD_REQUEST, 30007, "사진 등록 갯수를 초과했습니다."),


    /**
     * Study Error Type
     **/
    NOT_FOUND_STUDY(HttpStatus.NOT_FOUND, -30000, "스터디를 찾을 수 없습니다."),

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
