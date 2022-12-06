package com.tdns.toks.api.domain.ranking.model.dto;

import java.util.List;

import com.tdns.toks.core.domain.ranking.model.dto.RankingDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RankingApiDTO {

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Schema(name = "RankingsApiResponse", description = "Ranks 응답 모델")
	public static class RankingsApiResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "ranks", description = "rank 리스트")
		private List<RankingDto> rankings;
	}
}
