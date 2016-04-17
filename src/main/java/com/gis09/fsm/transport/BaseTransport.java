package com.gis09.fsm.transport;

/**
 * @author xiaohu
 * 2016年4月17日下午8:01:06
 * @description
 */
public abstract class BaseTransport implements Transport {
	public abstract void send(Object message);
}
