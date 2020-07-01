package com.fns.pocentitystates.porting.serviceprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fns.pocentitystates.porting.MobilePorting;
import com.fns.pocentitystates.porting.MobilePortingStatus;
import com.fns.pocentitystates.porting.repository.MobilePortingRepository;
import com.fns.pocentitystates.porting.support.MobilePortingNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MobilePortingServiceProvider {

    private final MobilePortingRepository repository;

    @Autowired
    public MobilePortingServiceProvider(MobilePortingRepository repository) {
        this.repository = repository;
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

        MobilePortingStatus newStatus = MobilePortingStatus.valueOf(status);

        MobilePorting porting = repository.findById(id)
                .orElseThrow(() -> new MobilePortingNotFoundException(id));

        MobilePortingStatus oldStatus = porting.getStatus();
        porting.setStatus(oldStatus.transitionTo(newStatus));

        // Save to DB
        repository.save(porting);

        return porting;

    }
}
