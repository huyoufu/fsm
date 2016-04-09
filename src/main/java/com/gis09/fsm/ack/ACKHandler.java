package com.gis09.fsm.ack;

import java.net.SocketException;
import java.net.UnknownHostException;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * 
 * @author xiaofuzi
 * 2016年4月5日
 * @description 握手
 */
public class ACKHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}
	public static void main(String[] args) throws SocketException, UnknownHostException {
	}
	
}
