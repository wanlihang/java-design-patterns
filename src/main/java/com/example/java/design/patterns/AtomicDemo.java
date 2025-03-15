package com.example.java.design.patterns;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args){
        count.incrementAndGet();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println(Thread.currentThread().getName() + ":" + count.incrementAndGet());
                }
            }).start();
        }

        try {
            Thread.sleep(1000);
            System.out.println(count.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
