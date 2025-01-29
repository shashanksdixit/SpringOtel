package io.sd.otel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;

@RestController
@RequestMapping("/service1")
public class Service1Controller {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(Service1Controller.class);

    @Value("${service.2.url}")
    private String service2Url;

    private final OpenTelemetry openTelemetry;

    public Service1Controller(RestTemplateBuilder restTemplateBuilder, OpenTelemetry openTelemetry) {
        this.restTemplate = restTemplateBuilder.build();
        this.openTelemetry = openTelemetry;
    }

    @GetMapping("/process")
    public String processRequest() {
        logger.info("Service-1 received request");
        addCount();
        String response = restTemplate.getForObject(service2Url + "/service2/process", String.class);
        logger.info("Service-1 received response from Service-2: {}", response);
        return "Service-1 -> " + response;
    }

    @GetMapping("/test")
    public String test() {
        logger.info("Service1 - Test");
        addCount();
        logger.info("Service-1 test endpoint called");
        return "Test : Service1 is UP and Running";
    }

    @GetMapping("/test2")
    public String test2() {
        logger.info("Service1 - Test");
        addCount();
        logger.info("Service-1 test2 endpoint called");
        return "Test2 : Service1 is UP and Running";
    }

    private void addCount() {
        Meter meter = openTelemetry.getMeter("service1");
        logger.debug("Service1 - Adding to the resquest counter");
        meter.counterBuilder("service1.requests.total").build().add(1);
    }

}