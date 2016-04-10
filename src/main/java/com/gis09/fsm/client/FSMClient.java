package com.gis09.fsm.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.gis09.fsm.boot.BootAble;
import com.gis09.fsm.common.config.FSMConfig;
/**
 * @author xiaohu
 * 2016年4月10日上午12:56:57
 * @description fsm客户端
 */
public class FSMClient implements BootAble {

	@Override
	public void boot() {
		boot(new FSMConfig());
	}

	@Override
	public void boot(FSMConfig fsmConfig) {
		
		
		
	}
	public void connect(String host,int port){
		EventLoopGroup group=new NioEventLoopGroup();
		try {
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
				}
			});
			ChannelFuture future = bootstrap.connect(host,port).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();
		}
		
	}

}
