package com.dangbun.domain.duty.exception.custom;

import com.dangbun.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class DutyNotFoundException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public DutyNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
