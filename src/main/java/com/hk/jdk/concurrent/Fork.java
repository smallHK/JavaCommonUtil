package com.hk.jdk.concurrent;

import java.lang.reflect.Field;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;

/**
 * @author smallHK
 * 2020/4/11 22:44
 */
public class Fork {

    private static void commonFactory() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool.getFactory();
        ForkJoinWorkerThread thread = factory.newThread(pool);
        System.out.println(thread.hashCode());
        try {
            Class clazz = thread.getClass().getSuperclass();
            System.out.println(clazz.getName());
            Field field = clazz.getDeclaredField("target");
            field.setAccessible(true);
            Runnable task = (Runnable)field.get(thread);
            System.out.println(task);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private static void commonPool() {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        System.out.println(pool.getParallelism());
        System.out.println(pool.getAsyncMode());
        System.out.println(pool.getPoolSize());
        System.out.println(pool.getStealCount());
        System.out.println(pool.getRunningThreadCount());
        System.out.println(pool.getQueuedSubmissionCount());
        pool.submit(() -> {
            System.out.println("任务开始！");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务结束！");

        });

        System.out.println("完成提交");
        pool.shutdown();

        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

//        commonPool();
        commonFactory();
    }
}
