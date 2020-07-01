package com.fns.pocentitystates.porting.rest.exceptionhandler;

import com.fns.pocentitystates.support.exception.CustomRuntimeException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomRuntimeException.class })
    public ResponseEntity<Object> handleMobilePortingNotFoundException(CustomRuntimeException ex, WebRequest request) {

        ExceptionSchema exceptionSchema = ExceptionSchema.builder()
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .timestamp(ex.getTimestamp())
                .build();

        return new ResponseEntity<>(exceptionSchema, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        ExceptionSchema exceptionSchema = ExceptionSchema.builder()
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(exceptionSchema, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleExceptionGeneric(Exception ex, WebRequest request) {

        ExceptionSchema exceptionSchema = ExceptionSchema.builder()
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(exceptionSchema, HttpStatus.BAD_REQUEST);
    }
}
