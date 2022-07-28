package com.zhang.common;

import org.springframework.stereotype.Component;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 * ThreadLocal对象是基于线程的不同 区分 每一个线程内都有一份域  不同线程之间有隔离性
 */

public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
