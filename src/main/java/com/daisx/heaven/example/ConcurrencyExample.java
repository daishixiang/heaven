package com.daisx.heaven.example;

import org.apache.poi.ss.formula.functions.T;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Author: dai.s.x
 * @Date: 2022/2/28 13:16
 */
public class ConcurrencyExample {

    private static final long count = 10000000L;

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency :" + time+"ms,b="+b);
    }
    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial:" + time+"ms,b="+b+",a="+a);
    }


    private static class Te{

    }

    public static void main(String[] args) throws Exception {
        Te t = new Te();
        ClassLayout.parseInstance(t).toPrintable();
    }
}
