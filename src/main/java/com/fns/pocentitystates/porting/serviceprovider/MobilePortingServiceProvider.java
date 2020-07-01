package com.fns.pocentitystates.porting.serviceprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fns.pocentitystates.porting.MobilePorting;
import com.fns.pocentitystates.porting.MobilePortingStatus;
import com.fns.pocentitystates.porting.repository.MobilePortingRepository;
import com.fns.pocentitystates.porting.support.exception.MobilePortingNotFoundException;
import com.fns.pocentitystates.porting.support.statemachine.Events;
import com.fns.pocentitystates.porting.support.statemachine.PortingStateMachine;
import com.fns.pocentitystates.support.exception.CustomRuntimeException;
import com.fns.pocentitystates.support.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MobilePortingServiceProvider {

    private final MobilePortingRepository repository;
    private final PortingStateMachine portingStateMachine;

    @Autowired
    public MobilePortingServiceProvider(MobilePortingRepository repository, @Lazy PortingStateMachine portingStateMachine) {
        this.repository = repository;
        this.portingStateMachine = portingStateMachine;
    }

    public MobilePorting get(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new MobilePortingNotFoundException(id));
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
        log.info("updateStatus %s %s", id, status);

        Events event = Events.valueOf(status);

        MobilePorting porting = repository.findById(id)
                .orElseThrow(() -> new MobilePortingNotFoundException(id));

        StateMachine<MobilePortingStatus, Events> stateMachine = portingStateMachine.buildMachine(porting.getStatus());
        boolean transitioned = stateMachine.sendEvent(event);

        if (!transitioned) {
            throw new IllegalArgumentException("Status not valid for current state: " + porting.getStatus().toString());
        }

        if (stateMachine.getExtendedState().getVariables().containsKey("error")) {
            throw (RuntimeException) stateMachine.getExtendedState().getVariables().get("error");
        }

        porting.setStatus(stateMachine.getState().getId());

        // Save to DB
        log.info("Saving porting to the DB: %s", porting);
        repository.save(porting);

        return porting;
    }

    public void doAsol() {
        log.info("doAsol stuff....");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new CustomRuntimeException(ErrorCode.PORTING_TRASITIONING_ERROR, e);
        }

        throw new IllegalArgumentException("ERROR!!!!");
    }
}
