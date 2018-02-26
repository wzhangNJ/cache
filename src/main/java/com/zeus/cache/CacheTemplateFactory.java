package com.zeus.cache;


/**
 * 缓存模版
 * @author wzhang
 *
 */
public class CacheTemplateFactory {
    
    public static  GuavaCacheTemplate createGuavaCacheTemplate() {
        return GuavaCacheTemplate.getInstance();
    }
}
