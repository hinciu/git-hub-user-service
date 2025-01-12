package com.core_will_soft.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorDetail> details;

    public ErrorResponse(final ErrorCode errorCode,
                         final List<ErrorDetail> details) {
        message = errorCode.getMessage();
        this.details = CollectionUtils.isEmpty(details) ? new ArrayList<>() : new ArrayList<>(details);
    }
}
