package com.tdns.toks.core.domain.rec.model;

import com.tdns.toks.core.common.model.converter.LongArrayConverter;
import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RecQuiz extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer round;

    private String categoryId;

    @Convert(converter = LongArrayConverter.class)
    private List<Long> pids;
}
