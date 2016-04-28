package com.gis09.fsm.subscription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohu
 * 2016年4月28日下午10:23:33
 * @description 服务器端的topic聚合类
 */
public class ServerTopic {
	Map<Topic, List<String>> topics=new HashMap<Topic, List<String>>();
	
}
