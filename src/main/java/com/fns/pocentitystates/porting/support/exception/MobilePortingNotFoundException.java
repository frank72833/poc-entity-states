package com.fns.pocentitystates.porting.support.exception;

import com.fns.pocentitystates.support.exception.CustomRuntimeException;
import com.fns.pocentitystates.support.exception.ErrorCode;

public class MobilePortingNotFoundException extends CustomRuntimeException {

    public MobilePortingNotFoundException(String id, Throwable cause, ErrorCode errorCode) {
        super(String.format("Mobile Porting with Id %s not found", id), errorCode, cause);
    }

    public MobilePortingNotFoundException(String id) {
        super(String.format("Mobile Porting with Id %s not found", id), ErrorCode.PORTING_NOT_FOUND, null);
    }
}
