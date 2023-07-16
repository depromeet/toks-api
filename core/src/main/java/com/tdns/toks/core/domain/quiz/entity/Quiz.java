package com.tdns.toks.core.domain.quiz.entity;

import com.tdns.toks.core.common.model.converter.MapConverter;
import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Quiz extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "category_id")
    private String categoryId;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> question;

    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '퀴즈 타입'")
    @Enumerated(EnumType.STRING)
    private QuizType quizType;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '퀴즈 설명'")
    private String description;

    @Column(columnDefinition = "TEXT COMMENT '정답'")
    private String answer;

    @Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
    private Long createdBy;

    @Column(nullable = false, name = "is_deleted")
    private Boolean deleted;
    
    public Quiz update(
            String title,
            String categoryId,
            QuizType quizType,
            Map<String, Object> question
    ) {
        this.title = title;
        this.categoryId = categoryId;
        this.quizType = quizType;
        this.question = question;

        return this;
    }
}
