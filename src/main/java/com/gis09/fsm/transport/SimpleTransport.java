package com.gis09.fsm.transport;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import com.gis09.fsm.ack.LoginHandler;
import com.gis09.fsm.business.BusinessReqHandler;
import com.gis09.fsm.business.MessageContainer;
import com.gis09.fsm.codec.MessageDecoder;
import com.gis09.fsm.codec.MessageEncoder;
import com.gis09.fsm.common.config.FSMConfig;
import com.gis09.fsm.heart.HeartReqHandler;

/**
 * @author xiaohu
 * 2016年4月17日下午8:07:13
 * @description 简单的消息发送
 */
public class SimpleTransport extends BaseTransport {
	private Bootstrap bootstrap=new Bootstrap();//创建一个启动器
	//channel的集合 有可能我们会连接不通地址的server
	private ConcurrentHashMap<String, Channel> channelTables=new ConcurrentHashMap<String, Channel>();
	@Override
	public void send(Object message) {
		MessageContainer.add(wrapper(message));
	}
	
	public void init(){
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				FSMConfig config=new FSMConfig();
				_init(config);
			}
		});
		thread.start();
	}
	public void _init(FSMConfig config){
		EventLoopGroup group = new NioEventLoopGroup();
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
			Channel channel = future.channel();
			channel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
