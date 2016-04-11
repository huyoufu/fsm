package com.gis09.fsm.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import org.jboss.marshalling.serial.SerialMarshallerFactory;

import com.gis09.fsm.message.Header;
import com.gis09.fsm.message.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
/**
 * @author xiaohu
 * 2016年4月10日下午8:26:19
 * @description 消息的解码类
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {
	MarshallingDecoder marshallingDecoder;
	public MessageDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength) throws Exception {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		this.marshallingDecoder=new MarshallingDecoder();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		//ByteBuf buf = (ByteBuf) super.decode(ctx, in);
		ByteBuf buf = (ByteBuf)in;
		if (buf==null) {
			return null;
		}
		Message message=new Message();
		Header header=new Header();
		header.setVersion(buf.readInt());
		header.setLength(buf.readInt());
		header.setSessionId(buf.readLong());
		header.setType(buf.readByte());
		header.setPriority(buf.readByte());
		
		int size=buf.readInt();//attachment的大小
		if (size>0) {
			Map<String, Object> attachment=new HashMap<String, Object>(size);
			int key_size=0;
			byte[] key_array=null;
			String key=null;
			for (int i = 0; i <size; i++) { //开始遍历map组装
				key_size=buf.readInt();
				key_array=new byte[key_size];
				buf.readBytes(key_array);
				key=new String(key_array,"utf-8");
				attachment.put(key, marshallingDecoder.decode(buf));
			}
			key_array=null;
			key=null;
			header.setAttachment(attachment);
		}
		if (buf.readableBytes()>4) {
			message.setBody(marshallingDecoder.decode(buf));
		}
		message.setHeader(header);
		return message;
	}
	class MarshallingDecoder{
		private final Unmarshaller unmarshaller;
		public MarshallingDecoder() throws Exception {
			super();
			this.unmarshaller = new SerialMarshallerFactory().createUnmarshaller(new MarshallingConfiguration());
		}
		protected Object decode(ByteBuf buf) throws Exception{
			int object_size=buf.readInt(); //对象大小
			ByteBuf slice = buf.slice(buf.readerIndex(), object_size);
			ByteInput input=new ChannelBufferByteInput(slice);
			try {
				unmarshaller.start(input);
				Object object=unmarshaller.readObject();
				unmarshaller.finish();
				buf.readerIndex(buf.readerIndex()+object_size);
				return object;
			} finally{
				unmarshaller.close();
			}
		}
	}
	class ChannelBufferByteInput implements ByteInput {

	    private final ByteBuf buffer;

	    public ChannelBufferByteInput(ByteBuf buffer) {
	        this.buffer = buffer;
	    }

	    @Override
	    public void close() throws IOException {
	        // nothing to do
	    }

	    @Override
	    public int available() throws IOException {
	        return buffer.readableBytes();
	    }

	    @Override
	    public int read() throws IOException {
	        if (buffer.isReadable()) {
	            return buffer.readByte() & 0xff;
	        }
	        return -1;
	    }

	    @Override
	    public int read(byte[] array) throws IOException {
	        return read(array, 0, array.length);
	    }

	    @Override
	    public int read(byte[] dst, int dstIndex, int length) throws IOException {
	        int available = available();
	        if (available == 0) {
	            return -1;
	        }

	        length = Math.min(available, length);
	        buffer.readBytes(dst, dstIndex, length);
	        return length;
	    }

	    @Override
	    public long skip(long bytes) throws IOException {
	        int readable = buffer.readableBytes();
	        if (readable < bytes) {
	            bytes = readable;
	        }
	        buffer.readerIndex((int) (buffer.readerIndex() + bytes));
	        return bytes;
	    }

	}


}
