package com.gis09.fsm.heart;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;
import com.gis09.fsm.session.Session;
import com.gis09.fsm.session.SessionContext;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * @author xiaohu 2016年4月11日下午9:25:15
 * @description 心跳请求处理
 */
public class HeartReqHandler extends ChannelHandlerAdapter {
	private Logger log=LoggerFactory.getLogger(getClass());
	@SuppressWarnings("rawtypes")
	private volatile ScheduledFuture heartbeat;
	private volatile SessionContext sessionContext; //集成session
	public HeartReqHandler() {
	}
	public HeartReqHandler(SessionContext sessionContext) {
		super();
		this.sessionContext = sessionContext;
	}


	public HeartReqHandler(Session session) {
	}
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message) msg;
		if ((message.getHeader() != null) && (message.getHeader().getType() == Header.TYPE_ACK_RESP)) {
			this.heartbeat = ctx.executor().scheduleAtFixedRate(new HeartTask(ctx), 0L, 15L, TimeUnit.SECONDS);
			ctx.fireChannelRead(msg);//而且还要处理业务信息了
		} else if ((message.getHeader() != null) && (message.getHeader().getType() == Header.TYPE_HEART_RESP)) {
			if (log.isInfoEnabled())
				log.info("the server heart response");
		} else
			ctx.fireChannelRead(msg);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (this.heartbeat != null) { 
			boolean cancel = this.heartbeat.cancel(true);
			if (cancel) {
				log.info("心跳停止成功");
			}else{
				log.info("心跳停止失败");
			}
			this.heartbeat = null;
		}
		ctx.close();//关闭当前链路
		throw new RuntimeException(" 发生异常 关闭客户端 等待重连");
	}

	private class HeartTask implements Runnable {
		private final ChannelHandlerContext ctx;

		public HeartTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		public void run() {
			log.info(" heart on " + System.currentTimeMillis());
			this.ctx.writeAndFlush(buildHeartReq());
		}

		private Message buildHeartReq() {
			Message message = new Message();
			Header header = new Header();
			header.setSessionId(1l);
			header.setType(Header.TYPE_HEART_REQ);
			message.setHeader(header);
			return message;
		}
	}
}
