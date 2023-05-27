package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.service.QuizApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizCreateResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizRequest;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizSimpleResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizzesResponse;

@Tag(name = "QuizController-V1", description = "QUIZ API")
@RestController
@RequestMapping(path = "/api/v1/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizController {
    private final QuizApiService quizApiService;

    @GetMapping("/{quizId}")
    @Operation(summary = "퀴즈 단건 조회", description = "상세 설명을 기록")
    public ResponseEntity<QuizSimpleResponse> getById(@PathVariable final Long quizId) {
        var response = quizApiService.getById(quizId);
        return ResponseDto.ok(response);
    }

    @GetMapping("/studies/{studyId}")
    @Operation(summary = "퀴즈 다건 조회")
    public ResponseEntity<QuizzesResponse> getAllByStudyID(@PathVariable final Long studyId) {
        var response = quizApiService.getAllByStudyId(studyId);
        return ResponseDto.ok(response);
    }

    @PostMapping
    @Operation(summary = "퀴즈 생성")
    public ResponseEntity<QuizCreateResponse> create(
            @Validated @RequestBody QuizRequest request
    ) {
        var response = quizApiService.create(request);
        return ResponseDto.ok(response);
    }
}
