package com.gis09.fsm.common.config;
/**
 * 
 * @author xiaofuzi
 * 2016年4月5日
 * @description 配置文件
 */
public class FSMConfig {
	public static final long FSM_HEART_INTERVAL = 45; // 默认的心跳间隔是45秒
	public static final long FSM_RECONNECT_INTERVAL = 60;// 重连间隔
	public static final long FSM_RECONNECT_NUM = 5;// 默认的重试次数 重试五次放弃
	public static final String FSM_DEFAULT_SERVER_HOST="127.0.0.1"; //默认绑定的地址
	public static final int FSM_DEFAULT_PORT=52018;//默认的启动端口
	private long fsm_heart_interval=FSM_HEART_INTERVAL;
	
	private long fsm_reconnect_interval=FSM_RECONNECT_INTERVAL;
	
	private long fsm_reconnect_num=FSM_RECONNECT_NUM;
	
	private String fsm_server_host=FSM_DEFAULT_SERVER_HOST;
	
	private int fsm_port=FSM_DEFAULT_PORT;
	public long getFsm_heart_interval() {
		return fsm_heart_interval;
	}
	
	
	public FSMConfig(long fsm_heart_interval, long fsm_reconnect_interval,
			long fsm_reconnect_num, String fsm_server_host, int fsm_port) {
		super();
		this.fsm_heart_interval = fsm_heart_interval;
		this.fsm_reconnect_interval = fsm_reconnect_interval;
		this.fsm_reconnect_num = fsm_reconnect_num;
		this.fsm_server_host = fsm_server_host;
		this.fsm_port = fsm_port;
	}
	
	public FSMConfig(String fsm_server_host, int fsm_port) {
		super();
		this.fsm_server_host = fsm_server_host;
		this.fsm_port = fsm_port;
	}


	public FSMConfig() {
		super();
	}

	public void setFsm_heart_interval(long fsm_heart_interval) {
		this.fsm_heart_interval = fsm_heart_interval;
	}

	public long getFsm_reconnect_interval() {
		return fsm_reconnect_interval;
	}

	public void setFsm_reconnect_interval(long fsm_reconnect_interval) {
		this.fsm_reconnect_interval = fsm_reconnect_interval;
	}

	public long getFsm_reconnect_num() {
		return fsm_reconnect_num;
	}

	public void setFsm_reconnect_num(long fsm_reconnect_num) {
		this.fsm_reconnect_num = fsm_reconnect_num;
	}

	public String getFsm_server_host() {
		return fsm_server_host;
	}

	public void setFsm_server_host(String fsm_server_host) {
		this.fsm_server_host = fsm_server_host;
	}

	public int getFsm_port() {
		return fsm_port;
	}

	public void setFsm_port(int fsm_port) {
		this.fsm_port = fsm_port;
	}


	@Override
	public String toString() {
		return "FSMConfig [fsm_heart_interval=" + fsm_heart_interval
				+ ", fsm_reconnect_interval=" + fsm_reconnect_interval
				+ ", fsm_reconnect_num=" + fsm_reconnect_num
				+ ", fsm_server_host=" + fsm_server_host + ", fsm_port="
				+ fsm_port + "]";
	}
	
}
