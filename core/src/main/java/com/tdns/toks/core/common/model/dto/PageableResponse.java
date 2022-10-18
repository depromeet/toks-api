package com.tdns.toks.core.common.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class PageableResponse<T> {
    private List<T> content;

    private int totalPages;
    private long totalElements;
    private int numberOfElements;
    private int size;
    private int number;
    private Sort sort;

    public static <T> PageableResponse<T> makeResponse(Page<T> data) {
        PageableResponse response = new PageableResponse();
        response.setContent(data.getContent());
        return getPageableResponse(response, data.getNumberOfElements(), data.getNumber(), data.getSize(), data.getTotalElements(), data.getTotalPages());
    }

    public static <T1, T2> PageableResponse<T2> makeResponse(Page<T1> data, List<T2> copyObjects) {
        PageableResponse response = new PageableResponse();
        response.setContent(copyObjects);

        int numberOfElement = copyObjects.size() != data.getNumberOfElements() ? copyObjects.size() : data.getNumberOfElements();
        return getPageableResponse(response, numberOfElement, data.getNumber(), data.getSize(), data.getTotalElements(), data.getTotalPages());
    }

    private static <T2> PageableResponse<T2> getPageableResponse(
            PageableResponse response, int numberOfElements, int number, int size, long totalElements,
            int totalPages) {
        response.setNumberOfElements(numberOfElements);
        response.setNumber(number);
        response.setSize(size);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        return response;
    }

}
