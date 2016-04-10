package com.gis09.fsm.ack;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * @author xiaohu
 * 2016年4月10日下午8:50:23
 * @description 登录处理器
 */
public class LoginHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLogin());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message=(Message) msg;
		if (message.getHeader()!=null&&message.getHeader().getType()==Header.TYPE_ACK_RESP) {
			Object body = message.getBody();
			if (body!=null) {
				System.out.println("登录失败");
				ctx.close();
			}else{
				System.out.println("登录成功");
				ctx.fireChannelRead(msg);
			}
		}else{
			ctx.fireChannelRead(msg);
		}
	}

	private Message buildLogin(){
		Message message=new Message();
		Header header=new Header();
		header.setType(Header.TYPE_ACK_REQ);
		message.setHeader(header);
		return message;
	}
}
