/*
 * Copyright (C) 2015-2018 ihome All rights reserved
 * Author: wzhang
 * Date: 2018/2/26 0026
 * Description:CacheAbstractTemplate.java
 */
package com.zeus.cache;

import com.alibaba.fastjson.TypeReference;

/**
 * 缓存模版抽象类
 * @author wzhang
 *
 */
public abstract class CacheAbstractTemplate {

    /**
     * 参数
     * @param key 
     * @param clazz
     * @param cacheLoadable db具体处理方法
     * @param expireTime
     * @param <T>
     * @return
     */
    public abstract <T> T queryCacheData(String key, TypeReference<T> clazz, CacheLoadable<T> cacheLoadable, int expireTime);
}
