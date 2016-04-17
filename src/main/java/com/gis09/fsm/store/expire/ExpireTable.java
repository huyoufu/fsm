package com.gis09.fsm.store.expire;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author xiaohu
 * 2016年4月17日下午6:32:00
 * @description 每一个库对应一个过期表
 */
public class ExpireTable {
	private int storeId;//所属store的id号
	private final Map<String, Long> table=new ConcurrentHashMap<String, Long>();
	public ExpireTable(int storeId) {
		super();
		this.storeId = storeId;
	}
	public void set(String key,long expiredTime){
		table.put(key, expiredTime);
	}
	public long TTL(String key){
		return 0;
	}
	public void add(String key,long expiredTime){
		table.put(key, expiredTime);
	}
	public void del(String key){
		table.remove(key);
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
}
