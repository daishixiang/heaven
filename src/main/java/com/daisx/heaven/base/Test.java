package com.daisx.heaven.base;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: daisx
 * @Date: 2021/6/15 13:49
 */
public class Test {

    public static void main(String[] args) {

        int test = test();
        System.out.println(test);
        RunTask task=new RunTask();
        new Thread(task).start();
    }

    private static  int test(){
        try {

            int i = 1/0;
            return i;
        }catch (Exception e){
            return 1;
        }finally {
            return 2;
        }
    }
}

class RunTask implements Runnable{
    @Override
    public void run() {

    }
}