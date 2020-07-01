package com.fns.pocentitystates.support;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomRuntimeExceptionSchema {
    private String message;
    private Long timestamp;
    private Long code;
}
