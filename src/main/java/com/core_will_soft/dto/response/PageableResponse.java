package com.core_will_soft.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> {

    private final List<T> data;
    private final Long offset;
    private final Long size;
    private final Long totalCount;

    @Builder
    public PageableResponse(final List<T> data,
                            final long offset, final long size, final long totalCount) {
        this.data = data;
        this.offset = offset;
        this.size = size;
        this.totalCount = totalCount;
    }
}
