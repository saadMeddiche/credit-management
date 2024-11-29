package com.saadmeddiche.creditmanagement.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestService {

    @Slf4j
    @AllArgsConstructor
    static class UpperCase implements Runnable {

        private final List<String> list;

        public void upperCase() {
            list.replaceAll(String::toUpperCase);
        }

        public void lowerCase() {
            list.replaceAll(String::toLowerCase);
        }

        @Override
        public void run() {
            this.upperCase();
            this.lowerCase();
            log.info("Value for Thread {} {}" , Thread.currentThread().getName() , this.list.toString());
        }
    }


    public static void main(String[] args) {
        List<String> list =  Collections.synchronizedList(new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")));
        //List<String> list =  new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        UpperCase upperCase = new UpperCase(list);
        for (int i = 10_000; i < 100_000; i++) {
            Thread t = new Thread(upperCase, "Thread-" + i);
            t.start();
        }
    }
}
