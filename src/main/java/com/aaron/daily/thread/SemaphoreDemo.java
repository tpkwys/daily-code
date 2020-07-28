package com.aaron.daily.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @program: daily-code
 * @description: 信号量案例，可以用于多个线程限流,创建一个线程的时候可以传入一个资源数量
 * 每次调用acquire就会减少一个资源，资源为0的时候，再调用该方法就会导致他们的线程阻塞状态
 * 除非调用release释放锁。
 * @author: tianpanke
 * @create: 2020-07-28
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        //停车位三个
        Semaphore semaphore=new Semaphore(3);
        for(int i=0;i<6;i++){
            new Thread(()->{
                try{
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getId()+":获得车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println(Thread.currentThread().getId()+":释放车位");
                    semaphore.release();
                }
            }).start();
        }

    }
}
