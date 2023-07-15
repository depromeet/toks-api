package com.tdns.toks.api.domain.suggest.model.dto;

import com.tdns.toks.core.domain.suggest.entity.Suggest;
import com.tdns.toks.core.domain.suggest.type.SuggestStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SuggestResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final SuggestStatus status;

    public static SuggestResponse of(Suggest suggest) {
        return new SuggestResponse(
                suggest.getId(),
                suggest.getTitle(),
                suggest.getContent(),
                suggest.getStatus()
        );
    }
}
