package com.tdns.toks.core.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthTokenType {

    BEARER_TYPE("Bearer ");

    private final String tokenType;
}

