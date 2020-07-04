package com.fns.pocentitystates.porting.serviceprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fns.pocentitystates.porting.MobilePorting;
import com.fns.pocentitystates.porting.MobilePortingStatus;
import com.fns.pocentitystates.porting.repository.MobilePortingRepository;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MobilePortingServiceProvider {

    private final MobilePortingRepository repository;
    private final StateMachineConfig<MobilePortingStatus, MobilePortingStatus> mobilePortingConfig;

    @Autowired
    public MobilePortingServiceProvider(MobilePortingRepository repository,
                                        StateMachineConfig<MobilePortingStatus, MobilePortingStatus> mobilePortingConfig) {
        this.repository = repository;
        this.mobilePortingConfig = mobilePortingConfig;
    }

    public Optional<MobilePorting> getMobilePorting(String id) {
        return repository.findById(id);
    }

    public MobilePorting startPorting(JsonNode request) {
        String ogMobile = request.get("original_mobile").asText();
        String orProvider = request.get("original_provider").asText();
        String newProvider = request.get("new_provider").asText();

        MobilePorting porting = MobilePorting.builder()
                .id(UUID.randomUUID().toString())
                .originalMobile(ogMobile)
                .originalProvider(orProvider)
                .newProvider(newProvider)
                .status(MobilePortingStatus.ANEN)
                .build();

        // Save to DB
        repository.save(porting);

        return porting;
    }

    public MobilePorting updateStatus(String id, String status) {
        MobilePortingStatus transitionStatus = MobilePortingStatus.valueOf(status);

        MobilePorting porting = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id not found"));

        StateMachine<MobilePortingStatus, MobilePortingStatus> mobilePortingStateMachine
                = new StateMachine<>(porting.getStatus(), mobilePortingConfig);

        mobilePortingStateMachine.fire(transitionStatus);

        MobilePortingStatus transitionedStatus = mobilePortingStateMachine.getState();
        porting.setStatus(transitionedStatus);

        return repository.save(porting);
    }

    public void onPortingCancelled() {
        log.info("onPortingCancelled started");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.warn("Error sleeping");
        }

        throw new RuntimeException("Error while cancelling Mobile porting");
    }

    public void onPortingCompleted() {
        log.info("onPortingCompleted started");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.warn("Error sleeping");
        }

        log.info("onPortingCompleted completed");
    }

    public void onPortingRejected() {
        log.info("onPortingRejected started");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.warn("Error sleeping");
        }

        log.info("onPortingRejected completed");
    }

    public void onPortingConfirmed() {
        log.info("onPortingConfirmed started");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.warn("Error sleeping");
        }

        log.info("onPortingConfirmed completed");
    }

    public void onPortingSolicited() {
        log.info("onPorting Solicited started");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.warn("Error sleeping");
        }

        log.info("onPortingSolicitated completed");
    }

    public void onPortingProcessed() {
        log.info("onPortingProcessed started");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.warn("Error sleeping");
        }

        log.info("onPortingProcessed completed");
    }
}
