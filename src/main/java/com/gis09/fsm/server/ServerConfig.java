package com.gis09.fsm.server;
/**
 * 
 * @author xiaofuzi
 * 2016年4月29日
 * @description 服务器端配置
 */
public class ServerConfig {
	public static final long DEFAULT_HEART_INTERVAL = 45; // 默认的心跳间隔是45秒
	public static final long DEFAULT_RECONNECT_INTERVAL = 15*1000;// 重连间隔
	public static final long DEFAULT_RECONNECT_NUM = 5;// 默认的重试次数 重试五次放弃
	public static final String DEFAULT_SERVER_HOST="127.0.0.1"; //默认绑定的地址
	public static final int DEFAULT_SERVER_PORT=52018;//默认的启动端口
	public static final int DEFAULT_READ_TIME_OUT=20; //读默认超时时间
	public static final int DEFAULT_READ_WRITE_OUT=20;//写默认超时时间
	/**
	 * 心跳间隔
	 */
	private long HeartInterval=DEFAULT_HEART_INTERVAL;
	/**
	 * 重连间隔
	 */
	private long reconnectInterval=DEFAULT_RECONNECT_INTERVAL;
	/**
	 * 重连次数
	 */
	private long reconnectNum=DEFAULT_RECONNECT_NUM;
	/**
	 * 服务器端口号
	 */
	private int serverPort=DEFAULT_SERVER_PORT;
	/**
	 * 读超时时间 单位秒
	 */
	private int readTimeOut=DEFAULT_READ_TIME_OUT;
	/**
	 * 写超时时间 单位秒
	 */
	private int writeTimeOut=DEFAULT_READ_WRITE_OUT;
	/**
	 * 是否需要密码验证 默认是不需要验证的
	 */
	private boolean needAuth=Boolean.FALSE;
	public long getHeartInterval() {
		return HeartInterval;
	}
	public void setHeartInterval(long heartInterval) {
		HeartInterval = heartInterval;
	}
	public long getReconnectInterval() {
		return reconnectInterval;
	}
	public void setReconnectInterval(long reconnectInterval) {
		this.reconnectInterval = reconnectInterval;
	}
	public long getReconnectNum() {
		return reconnectNum;
	}
	public void setReconnectNum(long reconnectNum) {
		this.reconnectNum = reconnectNum;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public int getReadTimeOut() {
		return readTimeOut;
	}
	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}
	public int getWriteTimeOut() {
		return writeTimeOut;
	}
	public void setWriteTimeOut(int writeTimeOut) {
		this.writeTimeOut = writeTimeOut;
	}
	public boolean isNeedAuth() {
		return needAuth;
	}
	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}
	@Override
	public String toString() {
		return "ServerConfig [HeartInterval=" + HeartInterval + ", reconnectInterval=" + reconnectInterval
				+ ", reconnectNum=" + reconnectNum + ", serverPort=" + serverPort + ", readTimeOut=" + readTimeOut
				+ ", writeTimeOut=" + writeTimeOut + ", needAuth=" + needAuth + "]";
	}
	
}
