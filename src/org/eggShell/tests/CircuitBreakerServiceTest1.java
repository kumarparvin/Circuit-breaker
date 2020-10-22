package org.eggShell.tests;

import org.eggShell.config.CircuitBreakerConfiguration;
import org.eggShell.core.AbstractCircuitBreaker;

public class CircuitBreakerServiceTest1 extends ServiceTest1 {

    private final static CircuitBreakerConfiguration circuitBreakerConfigurationServiceLevel =
            new CircuitBreakerConfiguration("CircuitBreakerServiceTest1", 0L, 2, 4);
    private final static CircuitBreakerConfiguration circuitBreakerConfigurationApiLevel =
            new CircuitBreakerConfiguration("CircuitBreakerServiceTestGetId", 0L, 1, 4);


    @Override
    public String getName() {
        return new AbstractCircuitBreaker<String> (circuitBreakerConfigurationServiceLevel) {

            @Override
            protected String run() {
                return CircuitBreakerServiceTest1.super.getName();
            }

            @Override
            protected String fallback() {
                return "Fallback name";
            }
        }.execute();
    }

    @Override
    public String getId() {
        return new AbstractCircuitBreaker<String> (circuitBreakerConfigurationApiLevel) {

            @Override
            protected String run() {
                return CircuitBreakerServiceTest1.super.getId();
            }

            @Override
            protected String fallback() {
                return "Fallback Id";
            }
        }.execute();
    }


    public static void main(String[] args) throws Exception {
        CircuitBreakerServiceTest1 c = new CircuitBreakerServiceTest1();

        int i = 0;
        while (i++ < 6) {
            System.out.println(c.getId());
       }
        i=0;
        while (i++ < 6) {
            System.out.println(c.getName());
        }
    }
}
