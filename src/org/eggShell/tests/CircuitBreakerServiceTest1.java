package org.eggShell.tests;

import org.eggShell.config.CircuitBreakerConfiguration;
import org.eggShell.core.AbstractCircuitBreaker;

public class CircuitBreakerServiceTest1 extends ServiceTest1 {

    private final static CircuitBreakerConfiguration circuitBreakerConfiguration =
            new CircuitBreakerConfiguration("CircuitBreakerServiceTest1", 5000L, 20, 50);


    @Override
    public String getName() {
        return new AbstractCircuitBreaker<String> (circuitBreakerConfiguration) {

            @Override
            protected String run() {
                return CircuitBreakerServiceTest1.super.getName();
            }

            @Override
            protected String fallback() {
                return "DUH";
            }
        }.execute();
    }

    @Override
    public String getId() {
        return new AbstractCircuitBreaker<String> (circuitBreakerConfiguration) {

            @Override
            protected String run() {
                return CircuitBreakerServiceTest1.super.getId();
            }

            @Override
            protected String fallback() {
                return "DUH Id";
            }
        }.execute();
    }
}
