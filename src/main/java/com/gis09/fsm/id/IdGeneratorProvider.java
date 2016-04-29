package com.gis09.fsm.id;
/**
 * @author xiaohu
 * 2016年4月17日下午6:47:24
 * @description id生成器的提供者
 */
public interface IdGeneratorProvider {
	/**
	 * 
	 * @author 户有福
	 * @return
	 * @description uuid生成
	 */
	String uuid();
	/**
	 * 
	 * @author 户有福
	 * @return
	 * @description 递增
	 */
	int increment();
	
	long timeRand();
}
