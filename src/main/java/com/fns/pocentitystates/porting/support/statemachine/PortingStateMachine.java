package com.fns.pocentitystates.porting.support.statemachine;

import com.fns.pocentitystates.porting.MobilePortingStatus;
import com.fns.pocentitystates.porting.serviceprovider.MobilePortingServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Slf4j
@Component
public class PortingStateMachine {

    private final MobilePortingServiceProvider mobilePortingServiceProvider;

    @Autowired
    public PortingStateMachine(MobilePortingServiceProvider mobilePortingServiceProvider) {
        this.mobilePortingServiceProvider = mobilePortingServiceProvider;
    }

    public StateMachine<MobilePortingStatus, Events> buildMachine(MobilePortingStatus initialState) {
        StateMachineBuilder.Builder<MobilePortingStatus, Events> builder = StateMachineBuilder.builder();

        try {
            builder.configureStates()
                    .withStates()
                    .initial(initialState)
                    .end(MobilePortingStatus.ACAN)
                    .end(MobilePortingStatus.APOR)
                    .end(MobilePortingStatus.AREC)
                    .states(EnumSet.allOf(MobilePortingStatus.class));

            builder.configureConfiguration()
                    .withConfiguration()
                    .autoStartup(true);

            builder.configureTransitions()
                    .withExternal()
                    .source(MobilePortingStatus.ANEN).target(MobilePortingStatus.ASOL)
                    .event(Events.ASOL)
                    .action(actionAsol(), actionException())
                    .and()
                    .withExternal()
                    .source(MobilePortingStatus.ANEN).target(MobilePortingStatus.ACAN)
                    .event(Events.ACAN)
                    .and()
                    .withExternal()
                    .source(MobilePortingStatus.ASOL).target(MobilePortingStatus.AREC)
                    .event(Events.AREC)
                    .and()
                    .withExternal()
                    .source(MobilePortingStatus.ASOL).target(MobilePortingStatus.ACAN)
                    .event(Events.ACAN)
                    .and()
                    .withExternal()
                    .source(MobilePortingStatus.ASOL).target(MobilePortingStatus.ACON)
                    .event(Events.ACON)
                    .and()
                    .withExternal()
                    .source(MobilePortingStatus.ACON).target(MobilePortingStatus.ACAN)
                    .event(Events.ACAN)
                    .and()
                    .withExternal()
                    .source(MobilePortingStatus.ACON).target(MobilePortingStatus.APOR)
                    .event(Events.APOR);
        } catch(Exception e) {
            throw new RuntimeException("An exception has occurred while initializing the state machine.");
        }

        return builder.build();
    }

    @Bean
    public Action<MobilePortingStatus, Events> actionAsol() {
        return (StateContext<MobilePortingStatus, Events> context) -> mobilePortingServiceProvider.doAsol();
    }


    @Bean
    public Action<MobilePortingStatus, Events> actionException() {
        return (StateContext<MobilePortingStatus, Events> context) -> {
            context.getExtendedState().getVariables().put("error", context.getException());
        };
    }
}
