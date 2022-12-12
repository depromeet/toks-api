package com.tdns.toks.core.domain.user.repository;

import java.util.List;

import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;

public interface UserCustomRepository {
	List<UserSimpleByQuizIdDTO> retrieveSubmittedByStudyId(Long studyId);

	List<UserSimpleDTO> retrieveParticipantByStudyId(Long studyId);
}
