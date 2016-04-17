package com.gis09.fsm.store;
/**
 * @author xiaohu
 * 2016年4月17日下午6:38:27
 * @description 仓库工厂类
 */
public interface StoreFactory {
	Store create(int id);
	Store create(String clazzName);
	Store create(Class<Store> clazz);
}
