package com.gis09.fsm.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
/**
 * @author xiaohu
 * 2016年4月17日下午6:59:56
 * @description id的存储器
 */
public class StoreIdContainer {
	private List<Integer> ids=new ArrayList<Integer>();
	public synchronized boolean hasKey(int id){
		return ids.contains(id);
	}
	public void add(int id){
		ids.add(id);
	}
}
