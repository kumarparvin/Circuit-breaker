package org.eggShell.core;

import org.eggShell.config.CircuitBreakerConfiguration;

public abstract class AbstractCircuitBreaker<T> extends CircuitBreakerObserver implements CircuitBreakerExecutor<T>  {


    private final ICircuitBreaker circuitBreaker;
    private final CircuitBreakerConfiguration circuitBreakerConfiguration;

    protected AbstractCircuitBreaker(CircuitBreakerConfiguration circuitBreakerConfiguration) {
        circuitBreaker = CircuitBreaker.Factory.getInstance(circuitBreakerConfiguration.getCircuitBreakerKeyName());
        this.circuitBreakerConfiguration = circuitBreakerConfiguration;
    }

    abstract protected T run();

    abstract protected T fallback();

    @Override
    final public T execute() {

        boolean failed = false;
        if(!circuitBreaker.isCircuitOpen()) {
            try {
                return run();
            } catch (Exception e) {
                failed = true;
                return fallback();
            } finally {
                circuitBreaker.registerCount(failed);
                circuitTripOnThresholdCross();
            }
        } else {
            System.out.println("Circuit is not closed");
            closeCircuitOnWindowTimExpire();
            return fallback();
        }
    }

     final protected void circuitTripOnThresholdCross() {
         System.out.println("check if Circuit needs to be opened");
         if(circuitBreaker.getTotalCount() >= circuitBreakerConfiguration.getNumberOfRequestToObserve()) {
            if(circuitBreaker.getFailureCount() >= circuitBreakerConfiguration.getThresholdToTripCircuit()) {
                circuitBreaker.tripCircuit();
            }
        }
    }

    final protected void closeCircuitOnWindowTimExpire() {
        System.out.println("check if Circuit needs to be closed");
       if (circuitBreaker.isCircuitOpen() && System.currentTimeMillis() - circuitBreaker.getCircuitLastTrippedTime() >= circuitBreakerConfiguration.getCircuitWindowOpenTime()) {
           circuitBreaker.closeTheCircuit();
       }
    }

}
