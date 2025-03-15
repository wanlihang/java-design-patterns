package com.example.java.design.patterns;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ReentrantLockDemo{

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    private static int state = 0;

    public static void main(String[] args) {
        // 创建线程
        Thread threadA = new Thread(() -> print("A", 0, conditionA, conditionB)); // 0 对应 A
        Thread threadB = new Thread(() -> print("B", 1, conditionB, conditionC)); // 1 对应 B
        Thread threadC = new Thread(() -> print("C", 2, conditionC, conditionA)); // 2 对应 C

        // 启动线程
        threadA.start();
        threadB.start();
        threadC.start();
    }

    public static void print(String flag, int count, Condition cur, Condition target) {
        //for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                while (state % 3 != count) {
                    cur.await();
                }
                System.out.println(flag);
                state++;
                target.signal();

            } catch(InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        //}
    }

}