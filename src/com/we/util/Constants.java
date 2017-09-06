package com.we.util;

public class Constants {
	public static String DEFAULT_TOKEN_MSG_JSP = "unSubmit.jsp" ;
    public static String TOKEN_VALUE ;
    public static String DEFAULT_TOKEN_NAME = "springMVCtoken";
    
    /**
     * 
     */
	public static final String SESSION_NAME = "lincomb_wxSessionID";

	/**
	 * cookie的生存周期 单位：秒 (1年)
	 */
    public static final int COOKIE_ALIVE_ONE_YEAR = 24 * 60 * 60 * 365;

	/**
	 * cookie的生存周期 单位：秒 (2周)
	 */
    public static final int COOKIE_ALIVE_TWO_WEEK = 24 * 60 * 60 * 14;

    /**
     * cookie生存周期 关闭浏览器就退出
     */
    public static final int COOKIE_ALIVE_NOT_SAVE = -1;

    /**
     * 获取用户采用的KEY
     */
    public static final String USER_SESSION = "USER_SESSION";

}
