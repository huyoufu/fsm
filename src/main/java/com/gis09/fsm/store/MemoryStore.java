package com.gis09.fsm.store;

import java.util.HashMap;
import java.util.Map;

import com.gis09.fsm.store.expire.ExpireTable;

/**
 * @author xiaohu
 * 2016年4月18日下午11:03:08
 * @description 内存仓库
 */
public class MemoryStore implements Store {
	private ExpireTable expireTable=new ExpireTable(getId());
	private Map<String, Object> container=new HashMap<String, Object>();
	private long id;
	public MemoryStore(long id) {
		super();
		this.id = id;
		expireTable=new ExpireTable(id);
	}

	@Override
	public void set(String key, StoreAble value) {
		container.put(key, value);
	}

	@Override
	public void set(String key, StoreAble value, long expiredTime) {
		expireTable.add(key, expiredTime);
		container.put(key, value);
	}

	@Override
	public StoreAble get(String key) {
		return null;
	}

	@Override
	public void del(String key) {
		
	}

	@Override
	public boolean supportExpired() {
		return false;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
