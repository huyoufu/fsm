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

}
