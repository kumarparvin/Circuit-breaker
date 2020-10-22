package org.eggShell.tests;


import java.util.concurrent.atomic.AtomicInteger;

public class ServiceTest1 {

    public static AtomicInteger count = new AtomicInteger();
    public String getName() {

        if(count.incrementAndGet()<3) {
            return "Name";
        } else if(count.incrementAndGet()>5){
            count.set(0);
        }
        throw new RuntimeException("something went wrong");
    }

    public String getId() {
        if(count.incrementAndGet()<3) {
            return "1";
        } else if(count.incrementAndGet()>5){
            count.set(0);
        }
        throw new RuntimeException("something went wrong");
    }
}
