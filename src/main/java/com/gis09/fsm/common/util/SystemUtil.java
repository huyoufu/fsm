package com.gis09.fsm.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Formatter;
import java.util.Locale;

/**
 * @author xiaohu 2016年4月5日下午8:19:38
 * @description 获取操作系统的帮助类
 */
public class SystemUtil {
	/**
	 * 
	 * @author 户有福
	 * @return 获取当前操作系统的唯一码
	 * @throws Exception
	 */
	public static String getCode() throws Exception {
		String mac = getMac();
		return mac;//目前先把mac地址作為唯一码
	}
	/**
	 * 获取操作系统的mac地址
	 * 
	 * @author 户有福
	 * @return
	 */
	public static String getMac() {
		String mac = "";
		try {
			InetAddress ia = InetAddress.getLocalHost();
			NetworkInterface byInetAddress = NetworkInterface
					.getByInetAddress(ia);
			byte[] bs = byInetAddress.getHardwareAddress();
			for (int i = 0; i < bs.length; i++) {
				mac += new Formatter().format(Locale.getDefault(), "%02X%s", bs[i],(i < bs.length - 1) ? "-" : "").toString();
			}
			return mac;
		} catch (Exception e) {
			e.printStackTrace();
			OSType osType = getOSType();
			if (osType.equals(OSType.WINDOWS)) {
				mac = getWindowsMac();
			}else{
				mac = getUnixMAC();
			}
			return mac;
		}
	}

	/**
	 * 获取操作系统的mac地址
	 * 
	 * @author 户有福
	 * @return
	 */
	public static String getWindowsMac() {
		String mac = null;  
        BufferedReader bufferedReader = null;  
        Process process = null;  
        try {  
            // windows下的命令，显示信息中包含有mac地址信息  
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    process.getInputStream(),"gbk"));
            String line = null;  
            int index = -1;  
            while ((line = bufferedReader.readLine()) != null) {  
                // 寻找标示字符串[physical  
                //index = line.toLowerCase().indexOf("physical address");  
                index = line.toLowerCase().indexOf("物理地址");  
                if (index >= 0) {// 找到了  
                    index = line.indexOf(":");// 寻找":"的位置  
                    if (index >= 0) {  
                        // 取出mac地址并去除2边空格  
                        mac = line.substring(index + 1).trim();  
                    }  
                    break;  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            bufferedReader = null;  
            process = null;  
        }  
  
        return mac; 
	}
	/**
	 * 获取操作系统类型
	 * 
	 * @author 户有福
	 */
	public static OSType getOSType() {
		String os = System.getProperty("os.name");
		if (os != null && os.length() > 0) {
			if (os.startsWith("Windows")) {
				return OSType.WINDOWS;
			}else{
				return OSType.LINUX;
			}
		}
		// 默认返回 linux操作系统类型 不过我们应该不用担心会出现这种情况
		return OSType.LINUX;
	}
	public static String getEncoding(){
		String encoding = System.getProperty("file.encoding");
		return encoding;
	}
	public static enum OSType {
		WINDOWS, LINUX;
	}

	/**
	 * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 如果有特殊系统请继续扩充新的取mac地址方法.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMAC() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// linux下的命令，一般取eth0作为本地主网卡
			process = Runtime.getRuntime().exec("ifconfig eth0");
			// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[hwaddr]
				index = line.toLowerCase().indexOf("hwaddr");
				if (index >= 0) {// 找到了
					// 取出mac地址并去除2边空格
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}
		return mac;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getMac());
		
	}
}
