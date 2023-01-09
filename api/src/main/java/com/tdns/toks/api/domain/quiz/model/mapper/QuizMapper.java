package com.tdns.toks.api.domain.quiz.model.mapper;

import com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {
    public Quiz toEntity(
            final QuizApiDTO.QuizRequest request
    ) {
        var endedAt = request.getStartedAt().plusSeconds(request.getDurationOfSecond());

        return Quiz.of(
                request.getQuestion(),
                request.getQuizType(),
                request.getDescription(),
                request.getAnswer(),
                request.getStartedAt(),
                endedAt,
                request.getImageUrls(),
                request.getStudyId(),
                request.getRound()
        );
    }
}
