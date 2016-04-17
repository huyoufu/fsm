package com.gis09.fsm.transport;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.gis09.fsm.ack.LoginHandler;
import com.gis09.fsm.business.BusinessReqHandler;
import com.gis09.fsm.business.MessageContainer;
import com.gis09.fsm.codec.MessageDecoder;
import com.gis09.fsm.codec.MessageEncoder;
import com.gis09.fsm.common.config.FSMConfig;
import com.gis09.fsm.heart.HeartReqHandler;
import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author xiaohu
 * 2016年4月17日下午8:07:13
 * @description 简单的消息发送
 */
public class SimpleTransport extends BaseTransport {
	@Override
	public void send(Object message) {
		MessageContainer.add(wrapper(message));
	}
	private Message wrapper(Object message){
		Message msg=new Message();
		Header header=new Header();
		header.setType(Header.TYPE_BI_REQ);
		msg.setHeader(header);
		msg.setBody(message);
		return msg;
	}
	public void init(){
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				FSMConfig config=new FSMConfig();
				_init(config);
			}
		});
		System.out.println("初始化");
		thread.setDaemon(false);
		System.out.println(thread.isDaemon());
		thread.start();
	}
	public void _init(FSMConfig config){
		System.out.println("初始化1");
		EventLoopGroup group = new NioEventLoopGroup();
		System.out.println("初始化2");
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new MessageDecoder(1024 * 1024, 4, 4,-8));
							ch.pipeline().addLast("messageEncoder",
									new MessageEncoder());
							ch.pipeline().addLast("readTimeoutHandler",
									new ReadTimeoutHandler(20));
							ch.pipeline().addLast("loginHandler",
									new LoginHandler());
							ch.pipeline().addLast("heartReqHandler",
									new HeartReqHandler());
							ch.pipeline().addLast("businessReqHandler",
									new BusinessReqHandler());
						}
					});
			ChannelFuture future = bootstrap.connect(config.getFsm_server_host(), config.getFsm_port()).sync();
			System.out.println("客户端连接成功");
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
