package com.gis09.fsm.ack;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * @author xiaohu
 * 2016年4月10日下午8:59:17
 * @description 处理登录请求handler
 */
public class LogonHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message=(Message) msg;
		if (message.getHeader()!=null&&message.getHeader().getType()==Header.TYPE_ACK_RESP) {
			System.out.println("登录成功");
			ctx.writeAndFlush(buildLogon());
		}else{
			ctx.fireChannelRead(msg);
		}
	}
	private Message buildLogon(){
		Message message=new Message();
		Header header=new Header();
		header.setType(Header.TYPE_ACK_RESP);
		message.setHeader(header);
		return message;
	}
}
