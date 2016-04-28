package com.gis09.fsm.store;
/**
 * @author xiaohu
 * 2016年4月18日下午10:38:26
 * @description 可存储对象
 */
public class StoreAbleObject implements StoreAble{
	private Object ori;//可存储对象的原有对象
	private long hits;//一共被命中多少次
	private long lastHitTime;//上次被查询的时间的毫秒值
	private long storedTime;//存储时间的毫秒值
	
	@Override
	public long getHits() {
		return hits;
	}
	@Override
	public long getlastHitTime() {
		return lastHitTime;
	}
	@Override
	public long getStoredTime() {
		return storedTime;
	}
	@Override
	public int compareTo(Object o) {
		return 0;
	}
	public Object getOri() {
		return ori;
	}
	public void setOri(Object ori) {
		this.ori = ori;
	}
	public long getLastHitTime() {
		return lastHitTime;
	}
	public void setLastHitTime(long lastHitTime) {
		this.lastHitTime = lastHitTime;
	}
	public void setHits(long hits) {
		this.hits = hits;
	}
	public void setStoredTime(long storedTime) {
		this.storedTime = storedTime;
	}
}
