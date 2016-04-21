package com.gis09.fsm.transport;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import com.gis09.fsm.message.Message;

/**
 * @author xiaohu 2016年4月17日下午8:07:13
 * @description 简单的消息发送
 */
public class SimpleTransport extends BaseTransport {
	private Bootstrap bootstrap = new Bootstrap();// 创建一个启动器
	private EventLoopGroup group = new NioEventLoopGroup();
	// channel的集合 有可能我们会连接不通地址的server
	private ConcurrentHashMap<String, Channel> channelTables = new ConcurrentHashMap<String, Channel>();
	private MessageContainer messageContainer;
	private FSMConfig fsmConfig;
	private ScheduledExecutorService executors;

	@Override
	public void send(Object message) {
		messageContainer.add(wrapper(message));
	}

	public void start() {
		try {
			ChannelFuture future= bootstrap.connect(fsmConfig.getFsm_server_host(),fsmConfig.getFsm_port()).sync();
			Channel channel = future.channel();
			channelTables.put(UUID.randomUUID().toString(), channel);
		} catch (InterruptedException e) {
			// 链接失败
		}
		executors = Executors.newScheduledThreadPool(2);
		executors.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				StartSend(channelTables);
			}
		}, 1, 3, TimeUnit.SECONDS); //无论怎么样每隔三秒启动一个发送循环
	}

	public void close() {
		Set<Entry<String, Channel>> entrySet = channelTables.entrySet();
		Iterator<Entry<String, Channel>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<java.lang.String, io.netty.channel.Channel> entry = (Map.Entry<java.lang.String, io.netty.channel.Channel>) iterator
					.next();
			Channel ch = entry.getValue();
			try {
				ch.closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		group.shutdownGracefully();
	}
	private void StartSend(ConcurrentHashMap<String, Channel> channelTables){
		Channel channel = getChannel();
		invokeSend(channel, messageContainer);
	}
	private void invokeSend(Channel ch,MessageContainer messageContainer){
		Message message = messageContainer.get();
		while (message!=null) {
			ch.writeAndFlush(message);
			message=messageContainer.get();
		}
	}
	private Channel getChannel(){
		Channel channel4send=null;
		Set<Entry<String, Channel>> entrySet = channelTables.entrySet();
		Iterator<Entry<String, Channel>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<java.lang.String, io.netty.channel.Channel> entry = (Map.Entry<java.lang.String, io.netty.channel.Channel>) iterator
					.next();
			Channel ch = entry.getValue();
			if (ch!=null) {
				channel4send=ch;
				break;
			}
		}
		return channel4send;
	}
	//初始化
	public void init(final FSMConfig config) {
		this.fsmConfig=config;
		this.messageContainer=new MessageContainer();
		bootstrap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ch.pipeline().addLast(
								new MessageDecoder(1024 * 1024, 4, 4, -8));
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
	}
	
	
	
}
