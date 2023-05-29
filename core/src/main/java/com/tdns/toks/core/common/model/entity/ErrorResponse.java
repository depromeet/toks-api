package com.tdns.toks.core.common.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> implements Serializable {
    private long timestamp;
    private String status;
    private String message;
    private T data;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public ErrorResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
        this.data = data;
    }

    public static Map emptyData() {
        return new HashMap();
    }
}
