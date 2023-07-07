package com.tdns.toks.core.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PageableResponseDto<T> {
    private List<T> data;
    private int page;
    private int size;
    private int totalPage;
    private long totalCount;

    public static <T> PageableResponseDto<T> ok(Page<T> data) {
        return new PageableResponseDto<T>(
                data.getContent(),
                data.getPageable().getPageNumber(),
                data.getSize(),
                data.getTotalPages(),
                data.getTotalElements()
        );
    }
}
