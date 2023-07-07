package com.tdns.toks.core.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorResponseDto implements Serializable {
    private final String errorCode;
    private final String reason;
}
