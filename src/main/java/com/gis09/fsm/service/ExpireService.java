package com.gis09.fsm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiaohu
 * 2016年4月17日下午7:26:12
 * @description 过期扫描服务
 */
public class ExpireService implements FSMService{
	private  final Logger log=LoggerFactory.getLogger(ExpireService.class);
	@Override
	public void start() {
		log.info("启动 key过期扫描服务");
	}

}
