package com.fns.pocentitystates.porting.support.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@Slf4j
@WithStateMachine
public class PortingStateMachineTransition {

    @OnTransition(target = "ANEN")
    void toAnen() throws InterruptedException {
        log.info("toAnen start");
        Thread.sleep(10000);
        log.info("toAnen end");
    }

    @OnTransition(target = "ASOL")
    void toAsol() throws InterruptedException {
        log.info("toAsol start");
        Thread.sleep(10000);
        log.info("toAsol end");
    }

    @OnTransition(target = "ACAN")
    void toAcan() throws InterruptedException {
        log.info("toAcan start");
        Thread.sleep(10000);
        log.info("toAcan end");
    }

    @OnTransition(target = "ACON")
    void toAcon() throws InterruptedException {
        log.info("toAcon start");
        Thread.sleep(10000);
        log.info("toAcon end");
    }

    @OnTransition(target = "APOR")
    void toApor() throws InterruptedException {
        log.info("toApor start");
        Thread.sleep(10000);
        log.info("toApor end");
    }

    @OnTransition(target = "AREC")
    void toAred() throws InterruptedException {
        log.info("toAred start");
        Thread.sleep(10000);
        log.info("toAred end");
    }
}
