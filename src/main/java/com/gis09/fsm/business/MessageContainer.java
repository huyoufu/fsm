package com.gis09.fsm.business;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.gis09.fsm.message.Message;

public class MessageContainer {
	@SuppressWarnings("unchecked")
	private static Queue<Message> wait2send=new ArrayBlockingQueue(1024);
	public static void add(Message message){
		wait2send.add(message);
	}
	public static Message get(){
		return wait2send.poll();
	}
}
