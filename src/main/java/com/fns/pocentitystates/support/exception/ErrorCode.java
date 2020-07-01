package com.fns.pocentitystates.support.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Porting
    PORTING_NOT_FOUND(10000, "Mobile Porting not found"),
    PORTING_TRASITIONING_ERROR(10001, "An error has occurred while transitioning porting."),
    ;

    @Getter
    private final int id;
    @Getter
    private final String message;

    ErrorCode(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
