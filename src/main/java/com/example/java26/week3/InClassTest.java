package com.example.java26.week3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class InClassTest {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition task1 = lock.newCondition();
    private final Condition task2 = lock.newCondition();

    private int a = 1;
    private char b = 'a';
    private static final CountDownLatch latch = new CountDownLatch(1);

    public void threadtask1() throws Exception{

        while(true){
            if(a >= 27){
                break;
            }
            lock.lock();
            try{
                System.out.print(a);
                a++;
                latch.countDown();
                task2.signal();
                task1.await();
            }finally {
                lock.unlock();
            }
        }
    }

    public void threadtask2() throws Exception{
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            if(Integer.valueOf(b) >= 96 + 27){
                break;
            }
            lock.lock();
            try{
                System.out.print(b);
                b++;
                task1.signal();
                task2.await();

            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InClassTest s1 = new InClassTest();
        Thread t1 = new Thread(() -> {
            try {
                s1.threadtask1();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                s1.threadtask2();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}