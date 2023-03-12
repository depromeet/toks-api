package com.tdns.toks.core.domain.study.repository;

import static com.tdns.toks.core.domain.study.model.entity.QStudy.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.type.StudyStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudyCustomRepositoryImpl implements StudyCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Study> retrieveByIdsAndStatuses(List<Long> ids, List<StudyStatus> statuses) {
		return queryFactory
			.selectFrom(study)
			.where(study.id.in(ids).and(inStatus(statuses)))
			.fetch();
	}

	private BooleanExpression inStatus(List<StudyStatus> statuses) {
		if (statuses.isEmpty()) {
			return null;
		}
		return study.status.in(statuses);
	}
}
