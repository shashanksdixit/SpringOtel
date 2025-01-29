package io.sd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;

@RestController
@RequestMapping("/service2")
public class Service2Controller {

    private static final Logger logger = LoggerFactory.getLogger(Service2Controller.class);
    private OpenTelemetry openTelemetry;

    public Service2Controller(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @GetMapping("/process")
    public String processRequest() {
        addCount();
        return "Service-2 response";
    }

    @GetMapping("/test")
    public String test() {
        logger.info("Service2 - Test");
        addCount();
        logger.info("Service-2 test endpoint called");
        return "Test : Service2 is UP and Running";
    }

    private void addCount() {
        Meter meter = openTelemetry.getMeter("service2");
        logger.debug("Service2 - Adding to the resquest counter");
        meter.counterBuilder("service2.requests.total").build().add(1);
    }


}
