package com.gis09.fsm.business;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * @author xiaohu
 * 2016年4月17日下午8:22:11
 * @description 业务消息处理器
 */
public class BusinessRespHandler extends ChannelHandlerAdapter  {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof Message) {
			Message message=(Message) msg;
			if (message.getHeader()!=null&&message.getHeader().getType()==Header.TYPE_BI_REQ) {
				//业务消息请求
				System.out.println(message.getBody());
			}
			
		}
	}
	
}
