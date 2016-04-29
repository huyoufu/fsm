package com.gis09.fsm.id;

import java.util.UUID;

public class MemoryIdGeneratorProvider implements IdGeneratorProvider {
	private static int incre=0;
	@Override
	public String uuid() {
		return UUID.randomUUID().toString();
	}

	@Override
	public synchronized int increment() {
		return incre++;
	}

	@Override
	public long timeRand() {
		return System.nanoTime(); //这里我们简单的使用纳秒值来做唯一值 可以说不会说问题的
	}

}
