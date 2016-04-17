package com.gis09.fsm.test.transport;

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
		transport.send("我要发送业务消息:小俊子你好");
		FSMConfig config=new FSMConfig();
		transport._init(config);
	}
}
