package com.tdns.toks.core.domain.user.entity;

import com.tdns.toks.core.common.model.converter.StringArrayConverter;
import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Table(name = "user_category")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;

    @Convert(converter = StringArrayConverter.class)
    private List<String> categoryIds;
}
