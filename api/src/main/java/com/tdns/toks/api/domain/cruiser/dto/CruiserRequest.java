package com.tdns.toks.api.domain.cruiser.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CruiserRequest {
    private final String text;
}
