package com.gis09.fsm.session;
/**
 * @author xiaohu
 * 2016年4月18日下午9:39:15
 * @description session上下文 或者说是容器
 */
public interface SessionContext {
	Session get(String sessionId);
	void setExpire(Session session,long expiredTime);
	void setExpire(String sessionId, long expiredTime);
	void add(Session session);
	void add(Session session,long expiredTime);
}
