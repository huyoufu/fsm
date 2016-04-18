package com.gis09.fsm.store;

import java.util.Date;

/**
 * @author xiaohu
 * 2016年4月18日下午10:32:00
 * @description 可存储对象的wrapper类
 */
public class StoreAbleObjectWrapper {
	public static StoreAble wrapper(Object object){
		StoreAbleObject storeAbleObject=new StoreAbleObject();
		storeAbleObject.setOri(object);
		storeAbleObject.setHits(0);
		long time=new Date().getTime();
		storeAbleObject.setLastHitTime(time);
		storeAbleObject.setStoredTime(time);
		return storeAbleObject;
	}
}
