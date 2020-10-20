package org.eggShell.config;

import java.util.Objects;

public final class CircuitBreakerConfiguration {

    private final String circuitBreakerKeyName;
    private final Long circuitWindowOpenTime;
    private final Integer thresholdToTripCircuit;
    private final Integer numberOfRequestToObserve;

    public CircuitBreakerConfiguration(String circuitBreakerKeyName, Long circuitWindowOpenTime,
                                       Integer thresholdToTripCircuit, Integer numberOfRequestToObserve) {

        Objects.requireNonNull(circuitBreakerKeyName);
        Objects.requireNonNull(circuitWindowOpenTime);
        Objects.requireNonNull(thresholdToTripCircuit);
        Objects.requireNonNull(numberOfRequestToObserve);

        assert thresholdToTripCircuit > 0: "thresholdToTripCircuit should be greater than 0";
        assert numberOfRequestToObserve > 0: "numberOfRequestToObserve should be greater than 0";

        this.circuitBreakerKeyName = circuitBreakerKeyName;
        this.circuitWindowOpenTime = circuitWindowOpenTime;
        this.thresholdToTripCircuit = thresholdToTripCircuit;
        this.numberOfRequestToObserve = numberOfRequestToObserve;
    }

    public String getCircuitBreakerKeyName() {
        return circuitBreakerKeyName;
    }

    public Long getCircuitWindowOpenTime() {
        return circuitWindowOpenTime;
    }

    public int getThresholdToTripCircuit() {
        return thresholdToTripCircuit;
    }

    public int getNumberOfRequestToObserve() {
        return numberOfRequestToObserve;
    }
}
