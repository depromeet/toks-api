package com.tdns.toks.core.domain.study.service;

import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.repository.StudyTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final StudyTagRepository studyTagRepository;

    public List<TagDTO> getStudyTagsDTO(Long studyId) {
        return studyTagRepository.getStudyTagsByStudyId(studyId).stream()
                .map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
    }
}
