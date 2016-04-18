package com.gis09.fsm.store;
/**
 * @author xiaohu
 * 2016年4月18日下午10:38:26
 * @description 可存储对象
 */
public class StoreAbleObject implements StoreAble{
	private Object ori;
	private long hits;
	private long lastHitTime;
	private long storedTime;
	
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
