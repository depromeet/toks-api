package com.tdns.toks.core.domain.auth.model;

import lombok.Data;

import static com.tdns.toks.core.common.security.Constants.TOKS_AUTH_HEADER_KEY;

@Data
public class AuthToken {
    private final String key;
    private final String token;

    public AuthToken(String token) {
        this.key = TOKS_AUTH_HEADER_KEY;
        this.token = token;
    }
}
