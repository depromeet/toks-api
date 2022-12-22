package com.tdns.toks.core.domain.study.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.study.repository.StudyUserRepository;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyUserService {
	private final StudyUserRepository studyUserRepository;

	public List<UserSimpleByQuizIdDTO> filterUnSubmitterByStudyId(Long studyId) {
		var submitters = studyUserRepository.retrieveSubmittedByStudyId(studyId);
		var participants = studyUserRepository.retrieveParticipantByStudyId(studyId);
		var result = new ArrayList<UserSimpleByQuizIdDTO>();

		for (UserSimpleByQuizIdDTO participant : participants) {
			var unSubmitters = new ArrayList<>(participant.getUsers());
			unSubmitters.removeAll(submitters.stream()
				.filter(submitter -> submitter.getQuizId().equals(participant.getQuizId()))
				.findFirst()
				.map(UserSimpleByQuizIdDTO::getUsers)
				.orElseGet(Collections::emptyList));
			result.add(new UserSimpleByQuizIdDTO(participant.getQuizId(), unSubmitters));
		}
		return result;
	}
}
