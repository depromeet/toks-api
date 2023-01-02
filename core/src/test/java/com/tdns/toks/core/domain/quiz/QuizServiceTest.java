package com.tdns.toks.core.domain.quiz;

import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.study.repository.StudyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class QuizServiceTest {
	private final QuizRepository quizRepository = mock(QuizRepository.class);
	private final QuizReplyHistoryRepository quizReplyHistoryRepository  = mock(QuizReplyHistoryRepository.class);
	private final StudyRepository studyRepository = mock(StudyRepository.class);

	private final QuizService sut = new QuizService(studyRepository, quizRepository, quizReplyHistoryRepository);

	@Test
	@DisplayName("퀴즈가 없으면 SilentApplicationErrorException 던진다")
	void quizFindTest() {
		when(quizRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(
			SilentApplicationErrorException.class,
			() -> sut.getOrThrow(1L)
		);
	}
}
