package com.team2.finalproject.domain.dispatchnumber.exception;

import com.team2.finalproject.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DispatchNumberErrorCode implements ErrorCode {
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
