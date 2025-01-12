package com.core_will_soft.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CONSTRAINT_VIOLATION(
            "The attempted action can not be performed as it violates one or more constraints"),
    RESOURCE_NOT_FOUND("The requested resource was not found"),
    FAILED_TO_CONNECT("Failed to connect to the GitHub");

    private final String message;
}
