package com.gis09.fsm.id;
/**
 * @author xiaohu
 * 2016年4月17日下午6:46:27
 * @description id生成器
 */
public class IdGenerator {
	private static final IdGeneratorProvider PROVIDER;
	static{
		PROVIDER=new MemoryIdGeneratorProvider();
		
		
		
		
	}
	/**
	 * 
	 * @author 户有福
	 * @return
	 * @description uuid生成
	 */
	public static String uuid(){
		return PROVIDER.uuid();
	}
	/**
	 * 
	 * @author 户有福
	 * @return
	 * @description 递增
	 */
	public static int increment(){
		return PROVIDER.increment();
	}
	/**
	 * 
	 * @description 根据时间生成的唯一码
	 * @return
	 */
	public static long timeRand(){
		return PROVIDER.timeRand();
	}
	public static void main(String[] args) {
		String uuid = uuid();
		System.out.println(uuid);
	}
}
