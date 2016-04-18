package com.gis09.fsm.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohu
 * 2016年4月17日下午7:21:15
 * @description 简单的消息容器
 */
public class SimpleStore implements Store {
	private static final boolean SUPPORTEXPIRED=false; //不支持过期操作
	private static final Map<String, StoreAble> container=new ConcurrentHashMap<String, StoreAble>();
	@Override
	public void set(String key, StoreAble value) {
		container.put(key, value);
	}

	@Override
	public void set(String key, StoreAble value, long expiredTime) {
		//不支持的过期操作
		container.put(key, value);
	}

	@Override
	public StoreAble get(String key) {
		return container.get(key);
	}

	@Override
	public void del(String key) {
		container.remove(key);
	}

	@Override
	public boolean supportExpired() {
		return SUPPORTEXPIRED;
	}

	@Override
	public long getId() {
		return 0;
	}
	
}
