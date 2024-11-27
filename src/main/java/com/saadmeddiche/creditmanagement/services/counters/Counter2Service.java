package com.saadmeddiche.creditmanagement.services.counters;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Counter2Service implements Runnable {

    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            log.error("Error while sleeping the thread", e);
        }
        counter.incrementAndGet();
    }

    public void decrement() {
        counter.decrementAndGet();
    }

    public int getValue() {
        return counter.get();
    }

    @Override
    public void run() {
        this.increment();
        this.decrement();
        log.info("Value for Thread {} {}" , Thread.currentThread().getName() , this.getValue());
    }

    public static void main(String[] args) {
        Counter2Service counter = new Counter2Service();
        for (int i = 10_000; i < 40_000; i++) {
            Thread t = new Thread(counter, "Thread-" + i);
            t.start();
        }
    }
}
