package com.gis09.fsm.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author xiaohu
 * 2016年3月31日下午7:31:30
 * @description md5文件验证工具类
 */
public class MD5CheckUtil implements Serializable {
	private static final long serialVersionUID = 1736165635237962346L;
	private static final Logger log = LoggerFactory
			.getLogger(MD5CheckUtil.class);
	protected static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("Md5Check messagedigest初始化失败", e);
		}
	}

	public static boolean checkMD5(String path, String MD5) throws IOException {
		if (MD5.equals("")) {
			log.warn("the MD5 you input is empty");
			throw new RuntimeException("MD5 could't be null or empty");
		}
		log.debug("the MD5 you input is::{}", MD5);
		String _MD5 = getMD5(path);
		return MD5.equals(_MD5);
	}

	public static String getMD5(String path) throws IOException {
		return getMD5(new File(path));
	}

	@SuppressWarnings("resource")
	public static String getMD5(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		long len=file.length();
		if (len>Integer.MAX_VALUE) {
			final byte[] buffer = new byte[6*1024*1024]; //设置6M的缓冲
			for(int n; (n = in.read(buffer)) != -1; ) {
				messagedigest.update(buffer, 0, n);
			}
			return bufferToHex(messagedigest.digest());
		}
		FileChannel ch = in.getChannel();
		long start = System.currentTimeMillis();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0L,
				len);
		messagedigest.update(byteBuffer);
		String md5 = bufferToHex(messagedigest.digest());
		long end = System.currentTimeMillis();
		long cost = end - start;
		log.debug("getMD5 cost time :{}ms ", Long.valueOf(cost));
		return md5;
	}

	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte[] bytes, int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[((bt & 0xF0) >> 4)];
		char c1 = hexDigits[(bt & 0xF)];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	public static void main(String[] args) throws IOException {
		long currentTimeMillis1 = System.currentTimeMillis();
		String md5 = getMD5("d://config.xml");
		String md51 = getMD5("d://config1.xml");
		//MD5Hash md5 = MD5Hash.digest(new  FileInputStream("F:\\系统构建\\Linux\\VirtualBox-4.2.18-88781-Win.exe"));
		long currentTimeMillis2 = System.currentTimeMillis();
		System.out.println(currentTimeMillis2-currentTimeMillis1);
		System.out.println(md5);
		System.out.println(md51);
	}
}