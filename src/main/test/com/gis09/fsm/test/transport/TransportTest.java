package com.gis09.fsm.test.transport;

import java.util.Date;

import org.junit.Test;

import com.gis09.fsm.common.config.FSMConfig;
import com.gis09.fsm.transport.SimpleTransport;
import com.gis09.fsm.transport.Transport;

public class TransportTest {
	@Test
	public void test1(){
		Transport transport=new SimpleTransport();
		transport.send("发送消息");
	}
	@Test
	public void sendTest(){
		SimpleTransport transport=new SimpleTransport();
		FSMConfig config=new FSMConfig();
		transport.init(config);
		transport.start();
		transport.send("我要发送业务消息:小明你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		transport.send("我要发送业务消息:小小明你好子你好");
		try {
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000);
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			transport.send("我要发送业务消息:小小明你好子你好"+new Date());
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
