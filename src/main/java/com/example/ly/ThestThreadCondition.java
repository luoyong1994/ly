package com.example.ly;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ThestThreadCondition {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        final AtomicInteger atomicInteger = new AtomicInteger();
        AtomicInteger consumeCount = new AtomicInteger();
        ArrayBlockingQueue<User> queue = new ArrayBlockingQueue<User>(5);
        //1、重入锁
        //生产
        int taskSize = 10;
        CountDownLatch countDownLatch = new CountDownLatch(taskSize);
        ExecutorService producer = Executors.newFixedThreadPool(3);
        Thread threadProducer = new Thread(() -> {
            for (int i = 0; i < taskSize; i++) {
                System.out.println(i);
                producer.submit(() -> {
                    User user = new User();
                    try {
                        user.setName("test" + atomicInteger.incrementAndGet());
                        Thread.sleep(2000);
                        queue.put(user);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });

        threadProducer.start();
        //消费
        ExecutorService consumer = Executors.newFixedThreadPool(5);
        int co = taskSize;
        while (co > 0) {
            User take = queue.take();
            consumer.submit(() -> {
                System.out.println(take);
                consumeCount.incrementAndGet();
                countDownLatch.countDown();
            });
            co--;
        }
        countDownLatch.await();
        producer.shutdown();
        consumer.shutdown();
    }
}
