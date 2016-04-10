package com.gis09.fsm.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gis09.fsm.client.FSMClient;
import com.gis09.fsm.common.config.FSMConfig;
import com.gis09.fsm.server.FSMServer;

/**
 * 
 * @author xiaofuzi 2016年4月5日
 * @description fsm启动类
 */
public class Booter {
	private final Logger log = LoggerFactory.getLogger(Booter.class);
	private FSMConfig fsmConfig;

	public Booter(FSMConfig fsmConfig) {
		super();
		this.fsmConfig = fsmConfig;
	}

	/**
	 * 
	 * @description 启动
	 */
	public void boot(BootAble bootAble) {
		if (fsmConfig == null) {
			fsmConfig = new FSMConfig();
			if (log.isInfoEnabled()) {
				log.info("the fsm_config is not be inited will be use default "+fsmConfig);
			}
		}
		_boot();
	}

	private void _boot() {

	}

	public FSMConfig getFsmConfig() {
		return fsmConfig;
	}

	public void setFsmConfig(FSMConfig fsmConfig) {
		this.fsmConfig = fsmConfig;
	}

	public static FSMServer newServer() {
		return new FSMServer();
	}

	public static FSMClient newClient() {
		return null;
	}

	public static void main(String[] args) {
		Booter booter=new Booter(null);
		booter.boot(null);
	}
}
