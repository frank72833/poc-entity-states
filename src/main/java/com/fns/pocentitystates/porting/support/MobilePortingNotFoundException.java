package com.fns.pocentitystates.porting.support;

import com.fns.pocentitystates.support.CustomRuntimeException;

public class MobilePortingNotFoundException extends CustomRuntimeException {
    public MobilePortingNotFoundException(String id) {
        super(String.format("Mobile Porting with Id %s not found", id), 100000l);
    }
}
