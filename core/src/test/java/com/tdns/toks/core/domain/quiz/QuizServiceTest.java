package com.tdns.toks.core.domain.quiz;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.service.QuizService;

public class QuizServiceTest {
	private final QuizRepository quizRepository = mock(QuizRepository.class);
	private final QuizReplyHistoryRepository quizReplyHistoryRepository  = mock(QuizReplyHistoryRepository.class);

	private final QuizService sut = new QuizService(quizRepository, quizReplyHistoryRepository);

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
