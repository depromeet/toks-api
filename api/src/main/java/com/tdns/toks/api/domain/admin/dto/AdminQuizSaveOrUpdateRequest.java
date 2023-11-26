package com.tdns.toks.api.domain.admin.dto;

import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class AdminQuizSaveOrUpdateRequest {
    private String title;
    private List<String> tags;
    private String categoryId;
    /**
     * Question Data는 Front에서 보내주는 데이터를 그대로 사용한다.
     */
    private Map<String, Object> question;
    private QuizType quizType;
    private String description;
    private String answer;
}
