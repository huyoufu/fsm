package com.gis09.fsm.business;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.gis09.fsm.message.Message;
/**
 * @author xiaohu
 * 2016年4月20日下午9:45:36
 * @description 待发送消息的容器
 */
public class MessageContainer {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Queue<Message> wait2send=new ArrayBlockingQueue(1024);
	public static void add(Message message){
		wait2send.add(message);
	}
	public static Message get(){
		return wait2send.poll();
	}
}
