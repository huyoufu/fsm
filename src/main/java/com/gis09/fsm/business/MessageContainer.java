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
	private static final int DEFAULT_PERCENT=80;
	private static final int DEFAULT_CAPACITY=1024;
	private int percent=DEFAULT_PERCENT; // 百分比
	private int capacity=DEFAULT_CAPACITY; //队列大小
	private  Queue<Message> wait2send;
	public MessageContainer(int percent) {
		super();
		new MessageContainer(percent,DEFAULT_CAPACITY);
	}
	public MessageContainer(int percent, int capacity) {
		super();
		this.percent = percent;
		this.capacity = capacity;
		init();
	}

	/**
	 * 
	 * @description 初始化 创建容器
	 */
	private void init(){
		wait2send=new ArrayBlockingQueue<Message>(capacity);
	}
	
	public MessageContainer() {
		new MessageContainer(DEFAULT_PERCENT,DEFAULT_CAPACITY);
	}
	public void add(Message message){
		wait2send.add(message);
	}
	public  Message get(){
		return wait2send.poll();
	}
	public int getPercent() {
		return percent;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
