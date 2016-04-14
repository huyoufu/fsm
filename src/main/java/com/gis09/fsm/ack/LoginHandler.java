package com.gis09.fsm.ack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * @author xiaohu
 * 2016年4月10日下午8:50:23
 * @description 登录处理器
 */
public class LoginHandler extends ChannelHandlerAdapter {
	private Logger log = LoggerFactory.getLogger(getClass());

	  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	  {
	    super.exceptionCaught(ctx, cause);
	  }

	  public void channelActive(ChannelHandlerContext ctx) throws Exception
	  {
	    ctx.writeAndFlush(buildLogin());
	  }

	  public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception
	  {
	    Message message = (Message)msg;
	    if ((message.getHeader() != null) && (message.getHeader().getType() == Header.TYPE_ACK_RESP)) {
	      Object body = message.getBody();
	      if (body != null) {
	        if (this.log.isInfoEnabled()) {
	          this.log.info("登录失败");
	        }
	        ctx.close();
	      } else {
	        if (this.log.isInfoEnabled()) {
	          this.log.info("登录成功 返回的sessionId 是 " + message.getHeader().getSessionId());
	        }
	        ctx.fireChannelRead(msg);
	      }
	    } else {
	      ctx.fireChannelRead(msg);
	    }
	  }

	  private Message buildLogin() {
	    Message message = new Message();
	    Header header = new Header();
	    header.setType(Header.TYPE_ACK_REQ);
	    message.setHeader(header);
	    return message;
	  }
}
