package com.gis09.fsm.transport;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

/**
 * @author xiaohu
 * 2016年4月17日下午8:01:06
 * @description
 */
public abstract class BaseTransport implements Transport {
	public abstract void send(Object message);
	protected Message wrapper(Object message){
		Message msg=new Message();
		Header header=new Header();
		header.setType(Header.TYPE_BI_REQ);
		msg.setHeader(header);
		msg.setBody(message);
		return msg;
	}
}
