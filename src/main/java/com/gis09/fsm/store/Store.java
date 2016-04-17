package com.gis09.fsm.store;
/**
 * @author xiaohu
 * 2016年4月14日下午11:04:33
 * @description 仓库 存储信息的仓库
 */
public interface Store {
	void set(String key,StoreAble value);
	void set(String key,StoreAble value,long expiredTime);
	StoreAble get(String key);
	void del(String key);
	boolean supportExpired();
}
