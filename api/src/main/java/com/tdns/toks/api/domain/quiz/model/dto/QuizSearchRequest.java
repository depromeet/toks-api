package com.tdns.toks.api.domain.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSearchRequest {
    private Set<String> categoryIds;
    private Integer page;
    private Integer size;
}
