package com.gis09.fsm.store;
/**
 * @author xiaohu
 * 2016年4月17日下午6:35:32
 * @description 可以存储的对象
 */
public interface StoreAble extends Comparable<Object>{
	Object getOri();
	long getHits();//获取一个存储对象的命中次数
	long getlastHitTime(); //获取一个对象的上次的命中次数
	long getStoredTime(); //获取一个对象的存储时间
}
