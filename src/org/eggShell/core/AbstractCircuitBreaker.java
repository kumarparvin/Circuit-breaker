package org.eggShell.core;

import org.eggShell.config.CircuitBreakerConfiguration;

public abstract class AbstractCircuitBreaker<T> implements CircuitBreakerExecutor<T> {


    private final ICircuitBreaker circuitBreaker;
    private final CircuitBreakerConfiguration circuitBreakerConfiguration;

    protected AbstractCircuitBreaker(CircuitBreakerConfiguration circuitBreakerConfiguration) {
        circuitBreaker = CircuitBreaker.Factory.getInstance(circuitBreakerConfiguration.getCircuitBreakerKeyName());
        this.circuitBreakerConfiguration = circuitBreakerConfiguration;
    }

    abstract protected T run();

    abstract protected T fallback();

    @Override
    public T execute() {

        boolean failed = false;
        if(!circuitBreaker.isCircuitOpen()) {
            try {
                return run();
            } catch (Exception e) {
                failed = true;
                throw e;
            } finally {
                circuitBreaker.registerCount(failed);
                circuitTripOnThresholdCross();
            }
        } else {
            closeCircuitOnWindowTimExpire();
            return fallback();
        }
    }

    private void circuitTripOnThresholdCross() {
        if(circuitBreaker.getTotalCount() >= circuitBreakerConfiguration.getNumberOfRequestToObserve()) {
            if(circuitBreaker.getFailureCount() >= circuitBreakerConfiguration.getThresholdToTripCircuit()) {
                circuitBreaker.tripCircuit();
            }
        }
    }

    private void closeCircuitOnWindowTimExpire() {
       if (circuitBreaker.isCircuitOpen() && System.currentTimeMillis() - circuitBreaker.getCircuitLastTrippedTime() >= circuitBreakerConfiguration.getCircuitWindowOpenTime()) {
           circuitBreaker.closeTheCircuit();
       }
    }

}
