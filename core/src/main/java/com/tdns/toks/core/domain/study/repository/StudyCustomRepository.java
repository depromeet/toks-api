package com.tdns.toks.core.domain.study.repository;

import java.util.List;

import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.type.StudyStatus;

public interface StudyCustomRepository {

	List<Study> retrieveByIdsAndStatuses(List<Long> ids, List<StudyStatus> statuses);
}
