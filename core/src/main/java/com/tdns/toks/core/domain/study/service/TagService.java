package com.tdns.toks.core.domain.study.service;

import com.tdns.toks.core.domain.study.model.dto.TagStaticsDTO;
import com.tdns.toks.core.domain.study.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public TagStaticsDTO countStatics() {
        var endAt = LocalDateTime.now();
        var startAt = endAt.minusDays(1);

        var allCount = tagRepository.count();
        var dailyCount = tagRepository.countByCreatedAtBetween(startAt, endAt);

        return new TagStaticsDTO(allCount, dailyCount);
    }
}