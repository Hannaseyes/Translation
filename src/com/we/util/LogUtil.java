package com.we.util;
/**
 * 打印日志公用方法
 * @author cht
 *
 */
public class LogUtil {
	/**
	 * 方法开始时用到的封装日志方法
	 * @param methodName 方法名
	 * @param msgContent 日志打印的内容
	 * @param className 类名
	 * @param URL 方法要走的URL地址
	 * @return 组合了日志打印的内容，以String类型返回
	 */
	public static String getStart(String methodName,String msgContent ,String  className,String URL){
		// Log logger = LogFactory.getLog(className);
		String msgLog = "";
		if (!"".equals(URL)){
			msgLog = "请求URL: "+URL;
		}
		msgLog =msgLog + "【 类名："+className+" 方法名："+methodName+msgContent+"】";
		return msgLog;		
	}
	
	/**
	 * 方法结束时用到的封装日志方法
	 * @param methodName 方法名
	 * @param msgContent 打印日志内容
	 * @param className 类名
	 * @return 组合了日志打印的内容，以String类型返回
	 */
	public static String getEnd(String methodName,String msgContent ,String  className){
		// Log logger = LogFactory.getLog(className);
		String msgLog ="【 类名："+className+" 方法名："+methodName+msgContent+"】";
		return msgLog;		
	}
	
	/**
	 * 通过三个参数，对加解密作了异常描述
	 * @param methodName 主要加密解密的方法名称
	 * @param messageException 加密解密失败描述的内容
	 * @param securityflag securityflag=1为加密，securityflag=2 为解密
	 * @return
	 */
	public static String getSecuriyLog(String methodName,String messageException,String securityflag){
		String msg ="";
		msg ="加密失败";
		msg=	methodName + msg;
		
		if(securityflag.equals("1")){
			msg ="加密失败";
			msg=	"【"+methodName + msg+messageException+"】";
		}
		
		if(securityflag.equals("1")){
			msg ="解密失败";
			msg=	"【"+methodName + msg+messageException+"】";
		}
		
		return msg;
		
	}
}
