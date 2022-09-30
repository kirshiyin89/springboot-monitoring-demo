package com.monitoring.demo.endpoints;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Endpoint(id = "customEndpoint")
@Component
public class CustomEndpoint {

    private final Counter myCounter;

    @ReadOperation
    public String customEndpoint(int id) {
        HashMap<Integer, String> fruitsMap = new HashMap<>();
        fruitsMap.put(1, "kiwi");
        fruitsMap.put(2, "apple");
        fruitsMap.put(3, "banana");
        myCounter.increment();
        return fruitsMap.get(id);
    }


    public CustomEndpoint(MeterRegistry registry) {
        myCounter = Counter
                .builder("mycustomcounter")
                .description("count url hits")
                .register(registry);
    }

}