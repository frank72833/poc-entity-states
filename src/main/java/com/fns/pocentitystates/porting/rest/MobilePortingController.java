package com.fns.pocentitystates.porting.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fns.pocentitystates.porting.MobilePorting;
import com.fns.pocentitystates.porting.serviceprovider.MobilePortingServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/mobile/porting", produces = MediaType.APPLICATION_JSON_VALUE)
public class MobilePortingController {

    private final MobilePortingServiceProvider serviceProvider;

    @Autowired
    public MobilePortingController(MobilePortingServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @PostMapping
    public MobilePorting sendMessage(@RequestBody JsonNode request) {
        return serviceProvider.startPorting(request);
    }

    @PostMapping("/{id}/status")
    public MobilePorting updateStatus(@PathVariable("id") String id, @RequestBody JsonNode request) {
        return serviceProvider.updateStatus(id, request.get("status").asText());
    }
}
