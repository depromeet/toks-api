package com.tdns.toks.api.domain.quizrank.controller;

import com.tdns.toks.api.domain.quizrank.model.dto.QuizRankApiDTO;
import com.tdns.toks.api.domain.quizrank.service.QuizRankApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated(since = "1", forRemoval = true)
@Tag(name = "QuizRankController-V1", description = " API")
@RestController
@RequestMapping(path = "/api/v1/quiz-ranks", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizRankController {
    private final QuizRankApiService quizRankApiService;

    @Deprecated(since = "1", forRemoval = true)
    @GetMapping("studies/{studyId}")
    @Operation(summary = "순위 조회")
    public ResponseEntity<QuizRankApiDTO.QuizRanksApiResponse> getByStudyId(@PathVariable final Long studyId) {
        var response = quizRankApiService.getByStudyId(studyId);
        return ResponseDto.ok(response);
    }
}
