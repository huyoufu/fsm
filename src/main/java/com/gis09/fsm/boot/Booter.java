package com.gis09.fsm.boot;

import com.gis09.fsm.client.FSMClient;
import com.gis09.fsm.common.config.FSMConfig;
import com.gis09.fsm.server.FSMServer;

/**
 * 
 * @author xiaofuzi
 * 2016年4月5日
 * @description fsm启动类
 */
public class Booter {
	private FSMConfig fsmConfig;
	public Booter(FSMConfig fsmConfig) {
		super();
		this.fsmConfig = fsmConfig;
	}
	/**
	 * 
	 * @description 启动
	 */
	public  void boot(){
		if (fsmConfig==null) {
			fsmConfig=new FSMConfig();
		}
		_boot();
	}
	private void _boot(){
		
	}
	public FSMConfig getFsmConfig() {
		return fsmConfig;
	}

	public void setFsmConfig(FSMConfig fsmConfig) {
		this.fsmConfig = fsmConfig;
	}
	public static FSMServer newServer(){
		return new FSMServer();
	}
	public static FSMClient newClient(){
		return null;
	}
}
