package com.fns.pocentitystates.porting.rest.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fns.pocentitystates.support.exception.ErrorCode;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionSchema {
    private String message;
    private Long timestamp;
    private ErrorCode errorCode;
}
