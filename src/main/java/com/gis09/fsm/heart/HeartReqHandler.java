package com.gis09.fsm.heart;

import java.util.concurrent.TimeUnit;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
/**
 * @author xiaohu
 * 2016年4月11日下午9:25:15
 * @description 心跳请求处理
 */
public class HeartReqHandler extends ChannelHandlerAdapter{
	@SuppressWarnings("rawtypes")
	private volatile ScheduledFuture heartbeat;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message=(Message) msg;
		if (message.getHeader()!=null&&message.getHeader().getType()==Header.TYPE_ACK_REQ) {
			//如果当前是登录请求 则开启心跳 心跳间隔是45秒
			heartbeat=ctx.executor().scheduleAtFixedRate(new HeartTask(ctx), 0, 45, TimeUnit.SECONDS);
		}else if (message.getHeader()!=null&&message.getHeader().getType()==Header.TYPE_HEART_RESP) {
			//如果是心跳回应 就是打印ok
			System.out.println("the server heart response");
		}else{
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		if (heartbeat!=null) {
			heartbeat.cancel(true);
			heartbeat=null;//将引用置空
		}
		super.exceptionCaught(ctx, cause);
	}

	/**
	 * @author xiaohu
	 * 2016年4月11日下午9:33:49
	 * @description 心跳任务
	 */
	private class HeartTask implements Runnable{
		private final ChannelHandlerContext ctx;
		
		public HeartTask(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public void run() {
			//返回心跳的回应
			ctx.writeAndFlush(buildHeartReq());
		}
		
		private Message buildHeartReq(){
			Message message=new Message();
			Header header=new Header();
			header.setType(Header.TYPE_HEART_REQ);
			message.setHeader(header);
			return message;
		}
		
	}
}
