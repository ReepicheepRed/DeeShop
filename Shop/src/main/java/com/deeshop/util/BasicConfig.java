/**
 * com.rightoo.li.util
 * BasicConfig.java
 * 2015年1月22日 下午4:36:17
 * @author: z```s
 */
package com.deeshop.util;

/**
 * <p></p>
 * 2015年1月22日 Administrator
 * @author: z```s
 */
public class BasicConfig {
	public static final String DEBUG_BASE =  "http://192.168.126.63:8101/";
//	public static final String DEBUG_BASE =  "http://115.29.247.29:8088/";
	public static final String DEMO_BASE = "";
	public static final String RELEASE_BASE = "http://api.10bbuy.com/";
	public static final String VERSION = State.DEBUG.toString();
	public enum State{
		DEBUG,RELEASE,DEMO
	}
}
