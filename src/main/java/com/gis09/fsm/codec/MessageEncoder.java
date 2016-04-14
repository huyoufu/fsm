package com.gis09.fsm.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.jboss.marshalling.ByteOutput;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.serial.SerialMarshallerFactory;

import com.gis09.fsm.message.Message;

/**
 * @author xiaohu 2016年4月6日下午10:27:20
 * @description Marshall的消息编码器
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
	MarshallingEncoder marshallingEncoder;
	
	public MessageEncoder() throws Exception {
		super();
		this.marshallingEncoder = new MarshallingEncoder();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		if (msg==null||msg.getHeader()==null) {
			throw new RuntimeException("the msg is null");
		}
		ByteBuf buf=out;
		buf.writeInt(msg.getHeader().getVersion());
		buf.writeInt(msg.getHeader().getLength());
		buf.writeLong(msg.getHeader().getSessionId());
		buf.writeByte(msg.getHeader().getType());
		buf.writeByte(msg.getHeader().getPriority());
		buf.writeInt(msg.getHeader().getAttachment().size());
		String key=null;
		byte[] key_array=null;
		Object value=null;
		for (Entry<String, Object> entry : msg.getHeader().getAttachment().entrySet()) {
			key=entry.getKey();
			key_array=key.getBytes("utf-8");
			buf.writeInt(key_array.length);
			buf.writeBytes(key_array);
			value=entry.getValue();
			marshallingEncoder.encode(msg, buf);
		}
		key=null;
		key_array=null;
		value=null;
		if (msg.getBody()!=null) {
			marshallingEncoder.encode(msg.getBody(), buf);
		}else{
			buf.writeInt(0);
		}
		buf.setInt(4, buf.readableBytes()); //这里是因为length的位置是4 所以
	}

	/*@SuppressWarnings("unused")
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg,
			List<Object> out) throws Exception {
		if (msg==null||msg.getHeader()==null) {
			throw new RuntimeException("the msg is null");
		}
		ByteBuf buf=Unpooled.buffer();
		buf.writeInt(msg.getHeader().getVersion());
		buf.writeInt(msg.getHeader().getLength());
		buf.writeLong(msg.getHeader().getSessionId());
		buf.writeByte(msg.getHeader().getType());
		buf.writeByte(msg.getHeader().getPriority());
		buf.writeInt(msg.getHeader().getAttachment().size());
		String key=null;
		byte[] key_array=null;
		Object value=null;
		for (Entry<String, Object> entry : msg.getHeader().getAttachment().entrySet()) {
			key=entry.getKey();
			key_array=key.getBytes("utf-8");
			buf.writeInt(key_array.length);
			buf.writeBytes(key_array);
			value=entry.getValue();
			marshallingEncoder.encode(msg, buf);
		}
		key=null;
		key_array=null;
		value=null;
		if (msg.getBody()!=null) {
			marshallingEncoder.encode(msg.getBody(), buf);
		}else{
			buf.writeInt(0);
		}
		buf.setInt(4, buf.readableBytes()); //这里是因为length的位置是4 所以
		out.add(buf);
	}*/
	

}

class MarshallingEncoder {
	private static final byte[] LENGTH_ARR = new byte[4];
	Marshaller marshaller;
	public MarshallingEncoder() throws Exception {
		super();
		this.marshaller = new SerialMarshallerFactory().createMarshaller(new MarshallingConfiguration());
	}
	protected void encode(Object msg,ByteBuf out) throws Exception {
		try {
			int length_pos=out.writerIndex();
			out.writeBytes(LENGTH_ARR);
			ByteOutput newOutput=new ChannelBufferByteOutput(out);
			marshaller.start(newOutput);
			marshaller.writeObject(msg);
			marshaller.finish();
			out.setInt(length_pos, out.writerIndex()-length_pos-4);
		} finally{
			marshaller.close();
		}
	}
	
	class ChannelBufferByteOutput implements ByteOutput {

	    private final ByteBuf buffer;

	    /**
	     * Create a new instance which use the given {@link ByteBuf}
	     */
	    public ChannelBufferByteOutput(ByteBuf buffer) {
	        this.buffer = buffer;
	    }

	    @Override
	    public void close() throws IOException {
	        // Nothing to do
	    }

	    @Override
	    public void flush() throws IOException {
	        // nothing to do
	    }

	    @Override
	    public void write(int b) throws IOException {
	        buffer.writeByte(b);
	    }

	    @Override
	    public void write(byte[] bytes) throws IOException {
	        buffer.writeBytes(bytes);
	    }

	    @Override
	    public void write(byte[] bytes, int srcIndex, int length) throws IOException {
	        buffer.writeBytes(bytes, srcIndex, length);
	    }

	    /**
	     * Return the {@link ByteBuf} which contains the written content
	     *
	     */
	    ByteBuf getBuffer() {
	        return buffer;
	    }
	}
	
}
