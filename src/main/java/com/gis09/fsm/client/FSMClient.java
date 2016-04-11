package com.gis09.fsm.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import com.gis09.fsm.ack.LoginHandler;
import com.gis09.fsm.boot.BootAble;
import com.gis09.fsm.codec.MessageDecoder;
import com.gis09.fsm.codec.MessageEncoder;
import com.gis09.fsm.common.config.FSMConfig;
import com.gis09.fsm.heart.HeartReqHandler;

/**
 * @author xiaohu 2016年4月10日上午12:56:57
 * @description fsm客户端
 */
public class FSMClient implements BootAble {
	private ScheduledExecutorService executorService = Executors
			.newScheduledThreadPool(1);

	@Override
	public void boot() {
		boot(new FSMConfig());
	}

	@Override
	public void boot(FSMConfig fsmConfig) {
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
									new MessageDecoder(1024 * 1024, 4, 4));
							ch.pipeline().addLast("messageEncoder",
									new MessageEncoder());
							ch.pipeline().addLast("readTimeoutHandler",
									new ReadTimeoutHandler(50));
							ch.pipeline().addLast("loginHandler",
									new LoginHandler());
							ch.pipeline().addLast("heartReqHandler",
									new HeartReqHandler());
						}
					});
			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
			group.shutdownGracefully();
		} finally {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(15);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					connect(host, port);// 发起重连
				}
			});
		}
	}

	public static void main(String[] args) {
		FSMClient client=new FSMClient();
		client.boot();
	}
}
