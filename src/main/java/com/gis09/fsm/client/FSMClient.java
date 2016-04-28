package com.gis09.fsm.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.gis09.fsm.ack.LoginHandler;
import com.gis09.fsm.codec.MessageDecoder;
import com.gis09.fsm.codec.MessageEncoder;
import com.gis09.fsm.common.config.ClientConfig;
import com.gis09.fsm.heart.HeartReqHandler;
import com.gis09.fsm.session.DefaultSessionContext;
import com.gis09.fsm.session.SessionContext;

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
 * 2016年4月10日上午12:56:57
 * @description fsm客户端
 */
public class FSMClient {
	private ScheduledExecutorService executorService = Executors
			.newScheduledThreadPool(1);
	private volatile SessionContext sessionContext =DefaultSessionContext.getInstance(); //这里是单例模式 //内置一个sessionContext 用来保存session
	
	public void boot() {
		boot(new ClientConfig());
	}

	public void boot(ClientConfig fsmConfig) {
		connect(fsmConfig.getFsm_server_host(), fsmConfig.getFsm_port());
	}

	public void connect(final String host, final int port) {
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
									new LoginHandler(sessionContext));//登录处理
							ch.pipeline().addLast("heartReqHandler",
									new HeartReqHandler(sessionContext)); //处理
						}
					});
			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(15000);
						connect(host, port);// 发起重连
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		FSMClient client=new FSMClient();
		ClientConfig config=new ClientConfig();
		config.setFsm_server_host("127.0.0.1");
		client.boot(config);
	}

	public SessionContext getSessionContext() {
		return sessionContext;
	}

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
	
}
