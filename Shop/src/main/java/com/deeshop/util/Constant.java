package com.deeshop.util;
import java.util.Locale;

public class Constant {

	public static final String VERSION = BasicConfig.VERSION;

	// 共享数据SharedPreferences文件名
	public static final String USER_PREFERENCES_NAME = "application_user";
	public static final String PREFERENCES_LOGIN_FIRST = "login_first";

	//Cookie Key
	public static final String COOKIE_NAME = "YMCookies";
    public static final String ACCESS_TOKEN = "accesstoken";
    public static final String REFRESH_TOKEN = "refreshtoken";

//	key
    public static final String USER = "user";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";


	/**
	 * <p>
	 * 根据版本获取服务基础页面
	 * </p>
	 * 
	 * @return 2015年12月17日 下午5:44:33
	 * @author: z```s
	 */
	public static String getBaseUrl() {
		String baseUrl = "";
		if (VERSION.toLowerCase(Locale.ENGLISH).equals("debug")) {
			baseUrl = BasicConfig.DEBUG_BASE;
		} else {
			if (VERSION.toLowerCase(Locale.ENGLISH).equals("release")) {
				baseUrl = BasicConfig.RELEASE_BASE;
			} else {
				baseUrl = BasicConfig.DEMO_BASE;
			}
		}
		return baseUrl;
	}
}
