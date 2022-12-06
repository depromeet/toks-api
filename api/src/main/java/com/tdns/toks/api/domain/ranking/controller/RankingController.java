package com.tdns.toks.api.domain.ranking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.ranking.model.dto.RankingApiDTO;
import com.tdns.toks.api.domain.ranking.service.RankingApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "RankingController-V1", description = "RANKING API")
@RestController
@RequestMapping(path = "/api/v1/ranking", produces = "application/json")
@RequiredArgsConstructor
public class RankingController {
	private final RankingApiService rankingApiService;

	@GetMapping("/{studyId}")
	@Operation(
		method = "GET",
		summary = "순위 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RankingApiDTO.RankingsApiResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<RankingApiDTO.RankingsApiResponse> getByStudyId(@PathVariable final Long studyId) {
		var response = rankingApiService.getByStudyId(studyId);
		return ResponseDto.ok(response);
	}
}
