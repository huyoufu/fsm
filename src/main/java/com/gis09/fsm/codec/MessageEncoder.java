package com.gis09.fsm.codec;

import java.util.List;

import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
/**
 * @author xiaohu
 * 2016年4月6日下午10:27:20
 * @description Marshall的消息编码器
 */
public class MessageEncoder extends MessageToMessageEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg,
			List<Object> out) throws Exception {
		
	}
	
}
class MarshallingEncoder{
	private static final byte[] LENGTH_ARR=new byte[4];
}
