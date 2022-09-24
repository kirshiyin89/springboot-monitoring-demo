package com.monitoring.demo.endpoints;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

@Endpoint(id = "customEndpoint")
@Component
public class CustomEndpoint {

    @ReadOperation
    public String customEndpoint(int id) {
        HashMap<Integer, String> fruitsMap = new HashMap<>();
        fruitsMap.put(1, "kiwi");
        fruitsMap.put(2, "apple");
        fruitsMap.put(3, "banana");
        myCounter.increment();
        return fruitsMap.get(id);
    }

    private final Counter myCounter;

    public CustomEndpoint(MeterRegistry registry) {
        myCounter = Counter
                .builder("mycustomcounter")
                .description("count url hits")
                .register(registry);
        Gauge.builder("list-size",fetchListSize()).register(registry);
    }

    public Supplier<Number> fetchListSize() {
        List<String> dummyList = new ArrayList<>();
        dummyList.add("test");
        return dummyList::size;
    }

}