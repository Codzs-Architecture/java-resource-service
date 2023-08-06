package com.codzs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LoggingExampleController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoggingExampleController.class);

    @GetMapping
    public String log() {
        LOGGER.trace("This is a TRACE level message");
        LOGGER.debug("This is a DEBUG level message");
        LOGGER.info("This is an INFO level message");
        LOGGER.warn("This is a WARN level message");
        LOGGER.error("This is an ERROR level message");
        return "See the log for details";
    }
}