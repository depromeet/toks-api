package com.tdns.toks.core.domain.study.model.dto;

import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class StudyDTO {

    private Long id;

    private String name;

    private int participants;

    private List<TagDTO> tags;

    private boolean unsolvedQuiz; // status

    public static StudyDTO of(Study study, List<Tag> tagList, int participants, boolean unsolvedQuiz){
        return StudyDTO.builder()
                .id(study.getId())
                .name(study.getName())
                .tags(tagList.stream().map(tag -> TagDTO.of(tag)).collect(Collectors.toList()))
                .participants(participants)
                .unsolvedQuiz(unsolvedQuiz)
                .build();
    }
}
