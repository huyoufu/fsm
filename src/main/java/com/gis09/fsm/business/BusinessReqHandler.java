package com.gis09.fsm.business;

import java.util.concurrent.TimeUnit;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * @author xiaohu
 * 2016年4月17日下午8:49:35
 * @description 业务请求处理类
 */
public class BusinessReqHandler extends ChannelHandlerAdapter  {
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//能走到这里肯定是已经登录过的
		System.out.println("登录成功后的了");
		ctx.executor().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Message message = MessageContainer.get();
				if (message!=null) {
					ctx.channel().writeAndFlush(message);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
		
	}
}
