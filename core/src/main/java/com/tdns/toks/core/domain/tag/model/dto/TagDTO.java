package com.tdns.toks.core.domain.tag.model.dto;

import com.tdns.toks.core.domain.tag.model.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class TagDTO {

    protected Long id;

    protected String name;

    public static TagDTO of(Tag tag){
        return TagDTO.of(tag.getId(), tag.getName());
    }
}
