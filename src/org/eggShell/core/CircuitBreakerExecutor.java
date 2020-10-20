package org.eggShell.core;

public interface CircuitBreakerExecutor<T> {

    T execute();
}
