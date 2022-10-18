package com.tdns.toks.core.common.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PageableParam {

    private final int DEFAULT_PAGE_SIZE = 50;
    private final int MAX_PAGE_SIZE = 10000;

    @Min(value = 0)
//    @ApiParam(value = "페이지 번호. 0 보다 큰 정수. 값을 입력하지 않으면 페이지네이션을 적용하지 않고 전체 데이터를 반환함.")
    protected int page = 0;

    @Positive
//    @ApiParam(value = "한 페이지에 나오는 레코드 수. 0 보다 큰 정수.")
    protected int pageSize = DEFAULT_PAGE_SIZE;

    //    @ApiParam(value = "정렬 기준. 선택하지 않으면 id로 정렬함.", allowableValues = "id,...")
    protected String[] sortProperty = {"id"};

    //    @ApiParam(value = "정렬 방식. 선택하지 않으면 기본값(Desc)로 자동설정함. `sortProperty`값이 없으면 이 값은 무시함.", allowableValues = "ASC,DESC", defaultValue = "DESC")
    protected Sort.Direction sortDirection = Sort.Direction.DESC;

    public Pageable getPageable() {
        this.checkAndSetPageableParam();

        return PageRequest.of(page, pageSize, Sort.by(sortDirection, sortProperty));
    }

    public void checkAndSetPageableParam() {
        if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
    }

    public int getOffset() {
        return this.getPage() * this.getPageSize();
    }
}
