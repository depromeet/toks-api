package com.tdns.toks.core.domain.study.repository;

import java.util.List;

import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;

public interface StudyUserCustomRepository {
	List<UserSimpleByQuizIdDTO> retrieveSubmittedByStudyId(Long studyId);

	List<UserSimpleByQuizIdDTO> retrieveParticipantByStudyId(Long studyId);
}
