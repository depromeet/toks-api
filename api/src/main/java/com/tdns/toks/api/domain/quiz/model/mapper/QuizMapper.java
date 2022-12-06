package com.tdns.toks.api.domain.quiz.model.mapper;

import java.time.LocalDate;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.type.StudyStatus;

public class QuizMapper {

	public Quiz toEntity(StudyApiDTO.StudyCreateRequest studyCreateRequest, Long userId) {
		return Study.builder()
			.name(studyCreateRequest.getName())
			.description(studyCreateRequest.getDescription())
			.startDate(studyCreateRequest.getStartDate())
			.endDate(studyCreateRequest.getEndDate())
			.status(
				StudyStatus.getStatus(studyCreateRequest.getStartDate(), studyCreateRequest.getEndDate(), LocalDate.now()))
			.capacity(studyCreateRequest.getCapacity())
			.studyUserCount(0)
			.leaderId(userId)
			.build();
	}

}
