package com.tdns.toks.api.batch.job;

import com.tdns.toks.core.domain.quiz.entity.Quiz;
import com.tdns.toks.core.domain.quiz.entity.QuizTag;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizTagRepository;
import com.tdns.toks.core.domain.tag.entity.Tag;
import com.tdns.toks.core.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizTagJob {
    private final QuizRepository quizRepository;
    private final TagRepository tagRepository;
    private final QuizTagRepository quizTagRepository;

    @Transactional
    public void runEveryHourJob() {
        quizRepository.findAll().forEach(this::processQuizTags);
    }

    private void processQuizTags(Quiz quiz) {
        quiz.getTags()
                .stream()
                .map(this::resolveTag)
                .map(tag -> resolveQuizTag(quiz, tag))
                .forEach(quizTagRepository::save);
    }

    private Tag resolveTag(String tag) {
        log.info("resolve tag : {}", tag);
        return tagRepository.findByName(tag)
                .orElse(tagRepository.save(Tag.builder().name(tag).build()));
    }

    private QuizTag resolveQuizTag(Quiz quiz, Tag tag) {
        log.info("resolve quiz tag / quizId : {} / tag : {}", quiz.getId(), tag.getId());
        return QuizTag.builder().quizId(quiz.getId()).tagId(tag.getId()).build();
    }
}
