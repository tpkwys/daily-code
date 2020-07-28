package com.aaron.daily.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @program: daily-code
 * @description: 减法计数器案例
 * @author: tianpanke
 * @create: 2020-07-28
 **/
public class CountDownLatchDemo {
    public static void main(String[] args)  throws InterruptedException{
        CountDownLatch countDownLatch=new CountDownLatch(6); //6匹马同时到达，则释放栅栏，让马儿尽情驰骋
        final Random random=new Random();
        for(int i=0;i<6;i++){
            new Thread(()->{
                try{
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                }catch (InterruptedException e){
                    System.out.println("gg");

                }
                System.out.println(String.format("马儿达到栅栏，等待栅栏开启"));
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println("自动触发栅栏开启，起飞吧，马儿");
    }
}
