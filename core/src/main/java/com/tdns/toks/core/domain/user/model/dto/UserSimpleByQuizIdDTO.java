package com.tdns.toks.core.domain.user.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleByQuizIdDTO {
	private Long quizId;
	private List<UserSimpleDTO> users;
}
