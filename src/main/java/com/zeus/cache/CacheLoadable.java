package com.zeus.cache;

/**
 * 缓存钩子
 * @param <T>
 */
public interface CacheLoadable<T> {
	public T load();
}
