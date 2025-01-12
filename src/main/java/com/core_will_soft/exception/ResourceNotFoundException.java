/******************************************************************************
 *                                                                            *
 * Copyright (c) 2021 by ACI Worldwide Inc.                                   *
 * All rights reserved.                                                       *
 *                                                                            *
 * This software is the confidential and proprietary information of ACI       *
 * Worldwide Inc ("Confidential Information"). You shall not disclose such    *
 * Confidential Information and shall use it only in accordance with the      *
 * terms of the license agreement you entered with ACI Worldwide Inc.         *
 ******************************************************************************/

package com.core_will_soft.exception;

import com.core_will_soft.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object target;
    private final String message;

    public ResourceNotFoundException(final ErrorCode errorCode, final Object target, String message) {
        super(message);
        this.errorCode = errorCode;
        this.target = target;
        this.message = message;
    }
}
