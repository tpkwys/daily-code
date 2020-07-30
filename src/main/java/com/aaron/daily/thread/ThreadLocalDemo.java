package com.aaron.daily.thread;

/**
 * @program: daily-code
 * @description:
 * @author: tianpanke
 * @create: 2020-07-29
 **/
public class ThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal=new ThreadLocal<>();
        ThreadLocal<String> threadLocal1=new ThreadLocal<>();
        threadLocal.set(100);
        threadLocal1.set("100");

        System.out.println(threadLocal.get());

        threadLocal.remove();

        System.out.println(threadLocal.get());
    }
}
