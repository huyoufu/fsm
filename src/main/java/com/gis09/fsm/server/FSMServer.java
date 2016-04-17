package com.gis09.fsm.server;

import com.gis09.fsm.ack.LogonHandler;
import com.gis09.fsm.business.BusinessRespHandler;
import com.gis09.fsm.codec.MessageDecoder;
import com.gis09.fsm.codec.MessageEncoder;
import com.gis09.fsm.heart.HeartRespHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class FSMServer {
	public void bind(int port){
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup slavers=new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(boss,slavers).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG,1024)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new MessageDecoder(1024*1024, 4, 4,-8));
					ch.pipeline().addLast("messageEncoder", new MessageEncoder());
					ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
					ch.pipeline().addLast("logonHandler",new LogonHandler());
					ch.pipeline().addLast("heartReqHandler",new HeartRespHandler());
					ch.pipeline().addLast("businessRespHandler",new BusinessRespHandler());
				}
			});
			ChannelFuture bind = bootstrap.bind(port).sync();
			bind.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			boss.shutdownGracefully();
			slavers.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
		FSMServer fsmServer=new FSMServer();
		fsmServer.bind(52018);
	}
}
