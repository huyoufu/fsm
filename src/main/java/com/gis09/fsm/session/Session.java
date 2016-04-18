package com.gis09.fsm.session;

import java.util.HashMap;
import java.util.Map;
/**
 * @author xiaohu
 * 2016年4月18日下午9:38:27
 * @description 会话
 */
public class Session {
	private String sessionId; //sessionID
	private Map<String, Object> attributes=new HashMap<String, Object>(); //session 内存储的信息
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
}
