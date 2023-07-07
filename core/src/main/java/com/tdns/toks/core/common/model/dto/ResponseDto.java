package com.tdns.toks.core.common.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto<T> implements Serializable {
    private T data;

    public static <T> ResponseEntity<ResponseDto<T>> ok(T data) {
        return ResponseEntity.ok(new ResponseDto<T>(data));
    }

    public static <T> ResponseEntity<ResponseDto<T>> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto<T>(data));
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
