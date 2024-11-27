package com.saadmeddiche.creditmanagement.services.counters;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Counter1Service implements Runnable {

    private int counter = 0;

    public void increment() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            log.error("Error while sleeping the thread", e);
        }
        counter++;
    }

    public void decrement() {
        counter--;
    }

    public int getValue() {
        return counter;
    }

    @Override
    public synchronized void run() {
        this.increment();
        this.decrement();
        log.info("Value for Thread {} {}" , Thread.currentThread().getName() , this.getValue());
    }

    public static void main(String[] args) {
        Counter1Service counter = new Counter1Service();
        for (int i = 10_000; i < 11_000; i++) {
            Thread t = new Thread(counter, "Thread-" + i);
            t.start();
        }
    }
}
