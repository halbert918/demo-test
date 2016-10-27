package com.zk.lock;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/1/7
 * @Description
 */
public class TestLock {

    public static void main(String[] args) {

        Counter counter = new Counter();
//        Thread thread1 = new Thread(counter);
//        Thread thread2 = new Thread(counter);
//        Thread thread3 = new Thread(counter);
//        thread1.start();
//        thread2.start();
//        thread3.start();
        for(int i = 0; i < 50; i++) {
            Thread thread = new Thread(counter);
            thread.start();
        }

    }

}


class Counter implements Runnable {

    private int count = 0;

    @Override
    public void run() {
//        System.out.println("Thread:" + Thread.currentThread().getName() + "       count = " + getCount());
        getCount();
    }

    public int getCount() {
        DistributedLock lock = new DistributedLock("/lock");
        lock.lock();
        System.out.println("Thread:" + Thread.currentThread().getName() + "       count = " + count);
        count++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread:" + Thread.currentThread().getName() + "       count = " + count);
        lock.unlock();
        return count;
    }

}