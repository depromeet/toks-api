package com.tdns.toks.api.domain.suggest.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SuggestRegisterRequest {
    private final String title;
    private final String content;
}
