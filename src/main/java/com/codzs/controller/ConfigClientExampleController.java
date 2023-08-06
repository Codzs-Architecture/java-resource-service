package com.codzs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientExampleController {
    @Value("${external.property}")
    private String externalProperty;

    @GetMapping(
            value = "/external-key", produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String getExternalKey() {
        return String.format("External key fetched is: %s", externalProperty);
    }
}
