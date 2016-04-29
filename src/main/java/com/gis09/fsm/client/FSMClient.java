package com.gis09.fsm.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.gis09.fsm.ack.LoginHandler;
import com.gis09.fsm.codec.MessageDecoder;
import com.gis09.fsm.codec.MessageEncoder;
import com.gis09.fsm.heart.HeartReqHandler;
import com.gis09.fsm.service.FSMService;
import com.gis09.fsm.session.Session;
import com.gis09.fsm.subscription.Topic;

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

/**
 * @author xiaohu 
 * 2016年4月10日上午12:56:57
 * @description fsm客户端
 */
public class FSMClient implements FSMService {
	private ScheduledExecutorService executorService = Executors
			.newScheduledThreadPool(1);
	private Bootstrap bootstrap = new Bootstrap();// 创建一个启动器
	private EventLoopGroup group = new NioEventLoopGroup();
	private Channel channel;
	private volatile Session session;
	private ClientConfig clientConfig;
	/**
	 * 构造函数 必须传入一个clientConfig
	 * @param clientConfig
	 */
	public FSMClient(ClientConfig clientConfig) {
		super();
		this.clientConfig = clientConfig;
		new LoginHandler(this);
	}

	public void connect() {
		final LoginHandler loginHandler=new LoginHandler(this);
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
			e.printStackTrace();
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
	//获取当前的session
	public Session getSession() {
		return session;
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
