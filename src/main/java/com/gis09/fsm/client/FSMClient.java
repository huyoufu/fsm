package com.gis09.fsm.client;

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

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gis09.fsm.ack.LoginHandler;
import com.gis09.fsm.codec.MessageDecoder;
import com.gis09.fsm.codec.MessageEncoder;
import com.gis09.fsm.heart.HeartReqHandler;
import com.gis09.fsm.service.FSMService;
import com.gis09.fsm.subscription.Queue;
import com.gis09.fsm.subscription.Topic;

/**
 * @author xiaohu 
 * 2016年4月10日上午12:56:57
 * @description fsm客户端
 */
public class FSMClient implements FSMService {
	private Logger logger=LoggerFactory.getLogger(getClass());
	private ScheduledExecutorService executorService = Executors
			.newScheduledThreadPool(1);
	private Bootstrap bootstrap = new Bootstrap();// 创建一个启动器
	private EventLoopGroup group = new NioEventLoopGroup();
	private Channel channel;
	private ClientConfig clientConfig;
	private List<Queue> queues; //该客户端订阅的queues 消息队列
	/**
	 * 构造函数 必须传入一个clientConfig
	 * @param clientConfig
	 */
	public FSMClient(ClientConfig clientConfig) {
		super();
		this.clientConfig = clientConfig;
	}

	public void connect() {
		final LoginHandler loginHandler=new LoginHandler();
		final HeartReqHandler heartReqHandler=new HeartReqHandler();
		try {
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
									new ReadTimeoutHandler(clientConfig.getReadTimeOut()));
							ch.pipeline().addLast("loginHandler",
									loginHandler);//登录处理
							ch.pipeline().addLast("heartReqHandler",
									heartReqHandler); //处理心跳
						}
					});
			ChannelFuture future = bootstrap.connect(clientConfig.getServerHost(), clientConfig.getServerPort()).sync();
			channel = future.channel();
			channel.closeFuture().sync();
		} catch (Exception e) {
			logger.error("client occur exception {}",e.getMessage());
		} finally {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(clientConfig.getReconnectInterval());
						connect();// 发起重连
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
	}
	public void addTopic(Topic topic){
		//添加一个topic
		if (channel.isOpen()) {
			
		}
	}
	@Override
	public void start() {
		
	}

	@Override
	public void shutDown() {
		
	}
	public ClientConfig getClientConfig() {
		return clientConfig;
	}
}
