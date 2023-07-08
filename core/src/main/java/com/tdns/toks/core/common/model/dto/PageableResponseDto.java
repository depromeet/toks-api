package com.tdns.toks.core.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class PageableResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private int totalPage;
    private long totalCount;

    public static <T> ResponseEntity<ResponseDto<PageableResponseDto<T>>> ok(Page<T> data) {
        var pageData = new PageableResponseDto<T>(
                data.getContent(),
                data.getPageable().getPageNumber(),
                data.getSize(),
                data.getTotalPages(),
                data.getTotalElements()
        );

        return ResponseDto.ok(pageData);
    }
}
