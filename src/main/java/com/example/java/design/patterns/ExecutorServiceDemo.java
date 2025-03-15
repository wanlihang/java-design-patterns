package com.example.java.design.patterns;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

    static class B {
        int b;
    }

    public static void func(B b) {
        //b = new B();
        b.b = 1;

    }

    public static void main(String[] args) {
        B b = new B();
        b.b = 0;
        func(b);
        System.out.println(b.b);
        StringBuilder sb = new StringBuilder("121");
    }
}
