package com.gis09.fsm.subscription;

/**
 * 
 * @author xiaofuzi
 * 2016年4月28日
 * @description 订阅主题
 */
public class Topic {
	public static final String SEPARATOR=".";
	/**
	 * 该主题名称
	 */
	private String name;
	
	public Topic(String name) {
		super();
		if (!checkName(name)) {
			throw new RuntimeException("主题名字不可为空,且只能为数字和字母组合");
		}
		this.name = trim(name);
	}

	public Topic(String name, Topic parent) {
		super();
		this.name = parent.getName()+SEPARATOR+name;
	}

	public String getName() {
		return name;
	}
	public Topic getParent() {
		return new Topic(name.substring(0,name.lastIndexOf(SEPARATOR)));
	}
	/**
	 * 
	 * @description 判断那么是否符合规范
	 * @return
	 */
	private boolean checkName(String name){
		if (name==null||name.length()<=0) {
			return false;
		}else{
			String reg_name = "^[a-zA-Z0-9\\.]+";
			return name.matches(reg_name);
		}
	}
	public static void main(String[] args) {
		Topic parent=new Topic("_11c.....om.the....111m...is");
		Topic topic=new Topic("xxx",parent);
		System.out.println(topic.getName());
		System.out.println(topic.getParent().getName());
		String reg="^[a-zA-Z0-9\\.]+";
		String name="www.themis.";
		System.out.println(name.matches(reg));
	}
	private String _trim(String content,String trim){
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			String temp=String.valueOf(content.charAt(i));
			if (trim.equals(temp)) {
				if (i>1) {
					String _tmp=String.valueOf(content.charAt(i-1));
					if (!trim.equals(_tmp)) {
						sb.append(temp);
					}
				}else{
					sb.append(temp);
				}
			}else{
				sb.append(temp);
			}
		}
		return sb.toString();
	}
	/**
	 * 去掉重复的分隔符
	 * @author 户有福
	 * @param content
	 */
	private String trim(String content){
		return _trim(content,SEPARATOR);
	}
}
