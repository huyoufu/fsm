package com.gis09.fsm.heart;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xiaohu
 * 2016年4月11日下午10:03:38
 * @description 心跳回应
 */
public class HeartRespHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message=(Message) msg;
		if (message.getHeader()!=null&&message.getHeader().getType()==Header.TYPE_HEART_REQ) {
			//如果当前是登录请求 则开启心跳 心跳间隔是45秒
			ctx.writeAndFlush(buildHeartResp());
		}else{
			ctx.fireChannelRead(msg);
		}
	}
	private Message buildHeartResp(){
		Message message=new Message();
		Header header=new Header();
		header.setType(Header.TYPE_HEART_RESP);
		message.setHeader(header);
		return message;
	}
}
