package com.zeus.cache;

import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/2/26 0026.
 */
public class CacheTemplateFactoryTest {

    final int THREAD_NUM = 10;
    final CountDownLatch latch = new CountDownLatch(THREAD_NUM);
    
    @Test
    public void queryCacheData() {
        //测试利用CountDownLatch来模拟并发

        for (int i = 1; i <= THREAD_NUM; i++) {
            new Thread(){
                public void run() {
                    try {
                        CacheTemplateFactory.createGuavaCacheTemplate().queryCacheData("key", new TypeReference<Student>() {}, new CacheLoadable<Student>() {
                            @Override
                            public Student load() {
                                System.out.println("数据库");
                                return new Student();
                            }
                        },1000);
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
        try {
            latch.await(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void queryDoubleLockCacheData() {
        //测试利用CountDownLatch来模拟并发

        for (int i = 1; i <= THREAD_NUM; i++) {
            new Thread(){
                public void run() {
                    try {
                        CacheTemplateFactory.createGuavaCacheTemplate().queryDoubleLockCacheData("key", new TypeReference<Student>() {}, new CacheLoadable<Student>() {
                            @Override
                            public Student load() {
                                System.out.println("数据库");
                                return new Student();
                            }
                        },1000);
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
        try {
            latch.await(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}