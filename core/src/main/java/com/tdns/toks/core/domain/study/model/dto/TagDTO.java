package com.tdns.toks.core.domain.study.model.dto;

import com.tdns.toks.core.domain.study.model.entity.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class TagDTO {

    protected Long id;

    protected String name;

    public static TagDTO of(Tag tag){
        return TagDTO.of(tag.getId(), tag.getName());
    }
}
