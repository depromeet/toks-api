package com.tdns.toks.core.domain.suggest.service;

import com.tdns.toks.core.domain.suggest.model.Suggest;
import com.tdns.toks.core.domain.suggest.repository.SuggestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SuggestService {
    private final SuggestRepository suggestRepository;

    @Transactional
    public Suggest save(Suggest suggest) {
        return suggestRepository.save(suggest);
    }
}
