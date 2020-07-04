package com.fns.pocentitystates.porting.support.statemachine;

import com.fns.pocentitystates.porting.MobilePortingStatus;
import com.fns.pocentitystates.porting.serviceprovider.MobilePortingServiceProvider;
import com.github.oxo42.stateless4j.StateMachineConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class MobilePortingStateMachineConfig {

    private final MobilePortingServiceProvider serviceProvider;

    @Autowired
    public MobilePortingStateMachineConfig(@Lazy MobilePortingServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Bean
    StateMachineConfig<MobilePortingStatus, MobilePortingStatus> mobilePortingConfig() {
        StateMachineConfig<MobilePortingStatus, MobilePortingStatus> mobilePortingConfig = new StateMachineConfig<>();

        mobilePortingConfig.configure(MobilePortingStatus.ANEN)
                .permit(MobilePortingStatus.ASOL, MobilePortingStatus.ASOL)
                .permit(MobilePortingStatus.ACAN, MobilePortingStatus.ACAN)
                .onEntry(() -> serviceProvider.onPortingProcessed());

        mobilePortingConfig.configure(MobilePortingStatus.ASOL)
                .permit(MobilePortingStatus.AREC, MobilePortingStatus.AREC)
                .permit(MobilePortingStatus.ACAN, MobilePortingStatus.ACAN)
                .permit(MobilePortingStatus.ACON, MobilePortingStatus.ACON)
                .onEntry(() -> serviceProvider.onPortingSolicited());

        mobilePortingConfig.configure(MobilePortingStatus.ACON)
                .permit(MobilePortingStatus.ACAN, MobilePortingStatus.ACAN)
                .permit(MobilePortingStatus.APOR, MobilePortingStatus.APOR)
                .onEntry(() -> serviceProvider.onPortingConfirmed());

        mobilePortingConfig.configure(MobilePortingStatus.AREC)
                .onEntry(() -> serviceProvider.onPortingRejected());

        mobilePortingConfig.configure(MobilePortingStatus.APOR)
                .onEntry(() -> serviceProvider.onPortingCompleted());

        mobilePortingConfig.configure(MobilePortingStatus.ACAN)
                .onEntry(() -> serviceProvider.onPortingCancelled());

        return mobilePortingConfig;
    }
}
