package com.example.java.design.patterns;

class SynchronizedDemo {

    private static Integer count = 1;

    private static final Object lock = new Object();

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ":" + count);
        Thread thread = new Thread(() -> print(true));
        Thread thread1 = new Thread(() -> print(false));

        thread.start();
        thread1.start();
    }

    private static void print(boolean flag) {
        while (count <= 100) {
            synchronized (lock) {
                if ((count % 2 == 1) == flag) {
                    System.out.println(Thread.currentThread().getName() + ":" + count++);
                    lock.notify();
                } else {
                    try {
                        lock.wait();
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }

    }


}
