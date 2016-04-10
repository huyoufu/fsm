package com.gis09.fsm.boot;

import com.gis09.fsm.common.config.FSMConfig;

public interface BootAble {
	
	void boot();
	
	void boot(FSMConfig fsmConfig);
}
