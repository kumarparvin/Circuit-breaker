package org.eggShell.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CircuitBreaker implements ICircuitBreaker{

    private AtomicBoolean isCircuitOpen = new AtomicBoolean(false);

    private AtomicInteger failureCount = new AtomicInteger(0);
    private AtomicInteger totalCount = new AtomicInteger(0);

    private AtomicLong CircuitLastTrippedTime = new AtomicLong(System.currentTimeMillis());

    private CircuitBreaker() {
    }

    @Override
    public int getFailureCount() {
        return failureCount.get();
    }

    @Override
    public int getTotalCount() {
        return totalCount.get();
    }

    @Override
    public Long getCircuitLastTrippedTime() {
        return CircuitLastTrippedTime.get();
    }

    @Override
    public void registerCount(boolean failure) {

        int total = totalCount.incrementAndGet();
        if(failure) {
           int count = failureCount.incrementAndGet();
        }
    }

    @Override
    public void tripCircuit() {
        if(!isCircuitOpen.getAndSet(true)) {
            CircuitLastTrippedTime.set(System.currentTimeMillis());
        }
    }

    @Override
    public boolean isCircuitOpen() {
        return isCircuitOpen.get();
    }

    @Override
    public void closeTheCircuit() {
        isCircuitOpen.set(false);
        failureCount.set(0);
        totalCount.set(0);
    }

    public static class Factory {

        final static Map<String, CircuitBreaker> map = new ConcurrentHashMap<>();

        public static CircuitBreaker getInstance(String CircuitBreakerKey) {
            return map.putIfAbsent(CircuitBreakerKey, new CircuitBreaker());
        }
    }


}
