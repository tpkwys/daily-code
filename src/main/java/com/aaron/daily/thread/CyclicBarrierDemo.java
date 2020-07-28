package com.aaron.daily.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @program: daily-code
 * @description: 加法计数器案例
 * @author: tianpanke
 * @create: 2020-07-28
 **/
public class CyclicBarrierDemo {
    public static void main(String[] args) throws BrokenBarrierException ,InterruptedException{
        CyclicBarrier cb=new CyclicBarrier(7,()->{
            System.out.println("召唤神龙");
        });
        final Random random=new Random();
        for(int i=0;i<7;i++){
            new Thread(()->{
                try{
                    System.out.println("收集龙珠");
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                    cb.await();
                    System.out.println("召唤");
                }catch (InterruptedException e){

                }catch (BrokenBarrierException e){

                }
            }).start();
        }
    }
}
