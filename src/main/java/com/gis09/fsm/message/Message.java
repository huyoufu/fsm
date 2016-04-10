package com.gis09.fsm.message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author xiaohu
 * 2016年3月29日下午11:13:42
 * @description 消息
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 768449427218300066L;
	private Header header; //消息头
	private Object body; //消息体
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public static void main(String[] args) throws Exception {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		FileOutputStream fileOutputStream=new FileOutputStream(new File("d://test.obj"));
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
		Message message=new Message();
		Header header=new Header();
		header.setLength(1111);
		objectOutputStream.writeObject(message);
		/*byte[] byteArray = out.toByteArray();
		System.out.println(byteArray.length);
		for (int i = 0; i < byteArray.length; i++) {
			System.out.println(byteArray[i]);
		}*/
	}
}
