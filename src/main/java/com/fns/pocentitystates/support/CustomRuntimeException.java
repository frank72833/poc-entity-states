package com.fns.pocentitystates.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomRuntimeException extends RuntimeException {
    private String message;
    private Long timestamp;
    private Long code;

    public CustomRuntimeException(String message, Long code) {
        this.message = message;
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }
}
