package com.gis09.fsm.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiaohu
 * 2016年4月17日下午6:43:02
 * @description 抽象工厂
 */
public abstract class AbstractStoreFactory implements StoreFactory {
	private final Logger log=LoggerFactory.getLogger(getClass());
	public boolean checkId(){
		return false;
	}

}
