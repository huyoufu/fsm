package com.gis09.fsm.session;

import com.gis09.fsm.id.IdGenerator;
import com.gis09.fsm.id.MemoryIdGeneratorProvider;
import com.gis09.fsm.store.MemoryStoreFactory;
import com.gis09.fsm.store.Store;
import com.gis09.fsm.store.StoreAble;
import com.gis09.fsm.store.StoreAbleObjectWrapper;

/**
 * @author xiaohu
 * 2016年4月18日下午9:41:42
 * @description 默认的session上下文
 */
public class DeafaultSessionContext implements SessionContext {
	private static final long DEFAULT_SESSION_EXPIRED_TIME=30*60*1000;//默认的session过期时间
	private Store store=new MemoryStoreFactory().create(IdGenerator.increment());//内置的session储存
	private static SessionContext context;
	private DeafaultSessionContext(){}
	public static SessionContext getInstance(){
		if (context == null) {
			synchronized (DeafaultSessionContext.class) {
				SessionContext temp = context;
				if (temp == null) {
					temp = new DeafaultSessionContext();
					context = temp;
				}
			}
		}
		return context;
	}
	@Override
	public Session get(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExpire(String sessionId,long expiredTime) {
		
	}
	@Override
	public void setExpire(Session session, long expiredTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void add(Session session) {
		add(session,DEFAULT_SESSION_EXPIRED_TIME);//默认30分钟过期
	}
	@Override
	public synchronized void add(Session session, long expiredTime) {
		boolean supportExpired = store.supportExpired();
		if (supportExpired) {
			store.set(session.getSessionId(),wrapper(session), expiredTime);
		}else{
			throw new RuntimeException("sessionContext的实现不支持过期操作");
		}
	}
	/**
	 * @description 装饰一些 以适合存储
	 * @author 户有福
	 * @param session
	 * @return
	 */
	private StoreAble wrapper(Session session){
		return StoreAbleObjectWrapper.wrapper(session);
	}

}
