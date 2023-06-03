package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.dto.comment.QuizCommentResponse;
import com.tdns.toks.core.domain.quizcomment.repository.QuizCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizCommentService {
    private final QuizCommentRepository quizCommentRepository;

    public Page<QuizCommentResponse> getAll(Long quizId, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizCommentRepository.findAllByQuizId(quizId, pageable).map(QuizCommentResponse::from);
    }
}
