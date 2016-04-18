package com.gis09.fsm.business;

import com.gis09.fsm.message.Message;
import com.gis09.fsm.store.StoreAble;

public class StoreMessage implements StoreAble {

	@Override
	public int compareTo(Object o) {
		return 0;
	}
	public StoreAble warpper(Message message){
		return null;
	}
	@Override
	public Object getOri() {
		return null;
	}
	@Override
	public long getHits() {
		return 0;
	}
	@Override
	public long getlastHitTime() {
		return 0;
	}
	@Override
	public long getStoredTime() {
		return 0;
	}

}
