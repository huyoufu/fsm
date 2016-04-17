package com.gis09.fsm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaohu
 * 2016年3月29日下午11:07:28
 * @description 消息传送系统的消息头
 */
public class Header {
	public static final Byte TYPE_BI_REQ=0x0; //业务请求消息
	public static final Byte TYPE_BI_RESP=0x1; //业务应答消息
	public static final Byte TYPE_BI_2R=0x2; //双通道业务消息
	public static final Byte TYPE_ACK_REQ=0x3; //ack_req 握手请求 消息
	public static final Byte TYPE_ACK_RESP=0x4; //ack_resp 握手应答消息
	public static final Byte TYPE_HEART_REQ=0x5; //heart_req 心跳请求消息
	public static final Byte TYPE_HEART_RESP=0x6; //heart_resp 心跳应答消息
	public static final Byte TYPE_CLOSE_REQ=0X7;//链路关闭请求消息
	public static final byte TYPE_CLOSE_RESP=0x8;//链路关闭回应消息
	public static final byte TYPE_PRIORITY_DEFAULT=0x17;
	private int version=0x20160101;//版本号
	private int length; //消息长度
	private long sessionId;//sessionId 
	private Byte type; //消息类型
	private Byte priority=TYPE_PRIORITY_DEFAULT; //消息优先级别
	private Map<String, Object> attachment=new HashMap<String, Object>(); //附件
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Byte getPriority() {
		return priority;
	}
	public void setPriority(Byte priority) {
		this.priority = priority;
	}
	public Map<String, Object> getAttachment() {
		return attachment;
	}
	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}
	
}
