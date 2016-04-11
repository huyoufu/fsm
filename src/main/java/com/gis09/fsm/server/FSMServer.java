package com.gis09.fsm.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class FSMServer {
	public void bind(int port){
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup slavers=new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(boss,slavers).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG,1024)
			.childHandler(null);//
			ChannelFuture bind = bootstrap.bind(port).sync();
			bind.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			boss.shutdownGracefully();
			slavers.shutdownGracefully();
		}
	}
}
