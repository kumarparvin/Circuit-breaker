package org.eggShell.core;

public abstract class CircuitBreakerObserver {

    protected abstract void circuitTripOnThresholdCross();
    protected abstract void closeCircuitOnWindowTimExpire();
}
