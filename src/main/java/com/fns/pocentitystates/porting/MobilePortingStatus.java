package com.fns.pocentitystates.porting;

import java.util.Collections;
import java.util.Set;

public enum MobilePortingStatus {
    ACAN(Collections.emptySet()),
    APOR(Collections.emptySet()),
    AREC(Collections.emptySet()),
    ACON(Set.of(ACAN, APOR)),
    ASOL(Set.of(AREC, ACAN, ACON)),
    ANEN(Set.of(ASOL, ACAN));

    private final Set<MobilePortingStatus> validTransitions;

    MobilePortingStatus(final Set<MobilePortingStatus> validTransitions) {
        this.validTransitions = validTransitions;
    }

    public MobilePortingStatus transitionTo(final MobilePortingStatus next) {
        if(!validTransitions.contains(next)) {
            throw new IllegalStateException();
        }
        return next;
    }
}
