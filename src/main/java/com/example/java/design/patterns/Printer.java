package com.example.java.design.patterns;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.checkerframework.checker.units.qual.C;

public class Printer {

    private static BlockingQueue<Object> bq = new ArrayBlockingQueue<>(16);

    private static final String A = "12345678";
    private static final String B = "abcdefgh";

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();

    private static Condition condition3 = lock.newCondition();

    private static int state = 0;

    public static void main(String[] args) {

        Thread a = new Thread(() -> print(A, 0, condition1, condition2));
        Thread b = new Thread(() -> print(B, 1, condition2, condition1));
        Thread c = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (bq.isEmpty()) {
                        Thread.sleep(1000);
                    }
                    System.out.println(Thread.currentThread().getName() + ":poll:" + bq.poll());
                    Thread.sleep(1000);
                    if (!a.isAlive() && !b.isAlive() && bq.isEmpty()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        a.start();
        b.start();
        //c.start();
    }

    private static void print(String out, int flag, Condition cur, Condition target) {
        int count = 0;
        while (count < out.length()) {
            lock.lock();
            try {
                while (state % 2 != flag) {
                    cur.await();
                }
                System.out.println(Thread.currentThread().getName() + ":offer:" + out.charAt(count));
                bq.offer(out.charAt(count));
                count++;
                state++;
                Thread.sleep(1000);
                target.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

}