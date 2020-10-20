package org.eggShell.core;

public interface ICircuitBreaker {

    void tripCircuit();

    boolean isCircuitOpen();

    Long getCircuitLastTrippedTime();

    void closeTheCircuit();

    int getFailureCount();

    int getTotalCount();

    void registerCount(boolean failure);
}
