package com.tdns.toks.core.domain.study.service;

import com.tdns.toks.core.domain.study.repository.StudyUserRepository;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyUserService {
    private final StudyUserRepository studyUserRepository;

    public List<UserSimpleByQuizIdDTO> filterUnSubmitterByStudyId(Long studyId) {
        var submitters = studyUserRepository.retrieveSubmittedByStudyId(studyId);
        var participants = studyUserRepository.retrieveParticipantByStudyId(studyId);

        return participants.stream()
                .map(participant -> {
                    var unSubmitters = participant.getUsers();

                    var filteredUser = submitters.stream()
                            .filter(submitter -> submitter.getQuizId().equals(participant.getQuizId()))
                            .findFirst()
                            .map(UserSimpleByQuizIdDTO::getUsers)
                            .orElseGet(Collections::emptyList);

                    unSubmitters.removeAll(filteredUser);

                    return new UserSimpleByQuizIdDTO(participant.getQuizId(), unSubmitters);
                }).collect(Collectors.toList());
    }
}
