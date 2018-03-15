package com.zeus.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.zeus.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCacheTemplate extends CacheAbstractTemplate {

    private GuavaCacheTemplate() {
    }

    private static class GuavaCacheTemplateHolder {
        private static final GuavaCacheTemplate INSTANCE = new GuavaCacheTemplate();
    }

    public static final GuavaCacheTemplate getInstance() {
        return GuavaCacheTemplateHolder.INSTANCE;
    }

    /**
     * 屏蔽缓存击穿->双重锁检查方案
     * @param key
     * @param clazz
     * @param cacheLoadable db具体处理方法
     * @param expireTime
     * @param <T>
     * @return
     */
    public <T> T queryDoubleLockCacheData(String key, TypeReference<T> clazz, CacheLoadable<T> cacheLoadable, int expireTime) {
        String json = getCacheDate(key);
        if (StringUtils.isNotEmpty(json) && !Objects.equals(json, "null")) {
            return JSON.parseObject(json, clazz);
        }
        synchronized (this) {
            json = getCacheDate(key);
            if (StringUtils.isNotEmpty(json) && !Objects.equals(json, "null")) {
                return JSON.parseObject(json, clazz);
            }
            T result = cacheLoadable.load();// 主要方法
            put(key, JsonUtils.obj2json(result));
            return result;
        }
    }

    /**
     * 存在缓存击穿
     * @param key
     * @param clazz
     * @param cacheLoadable db具体处理方法
     * @param expireTime
     * @param <T>
     * @return
     */
    public <T> T queryCacheData(String key, TypeReference<T> clazz, CacheLoadable<T> cacheLoadable, int expireTime) {
        String json = getCacheDate(key);
        if (StringUtils.isNotEmpty(json) && !Objects.equals(json, "null")) {
            return JSON.parseObject(json, clazz);
        }
        T result = cacheLoadable.load();// 主要方法
        put(key, JsonUtils.obj2json(result));
        return result;
    }

    private String getCacheDate(String key) {
        Object obj = null;
        try {
            obj = get(key);
        } catch (Exception e) {
        }
        if (null != obj) {
            System.out.println("缓存");
            return String.valueOf(obj);
        }
        return "null";
    }

    private LoadingCache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(20)
            .expireAfterWrite(5, TimeUnit.MINUTES).refreshAfterWrite(1, TimeUnit.MINUTES).recordStats()
            .build(new CacheLoader<Object, Object>() {
                @Override
                public Object load(Object key) throws Exception {
                    return "";
                }
            });

    private Object get(Object key) throws ExecutionException {
        return cache.getIfPresent(key);
    }

    private synchronized void put(Object key, Object value) {
        cache.put(key, value);
    }
}
