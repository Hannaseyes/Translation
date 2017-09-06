package com.we.util;


public final class Consts {
	// 返回码
	public static final String SUCCESS_CODE = "00000";
	public static final String CHECK_CODE = "00001";
	public static final String SESSION_LOSE_CODE = "00002";
	public static final String ERROR_CODE = "00003";
	public static final String SECURITY_CODE = "00004";
	public static final String EXCEPTION_CODE = "99999";
	public static final String PHNONE_CODE = "00010";// 手机是否存在checkcode
	public static final String SUCCESS = "success";
	public static final String SUCCESS_DESCRIBE = "success";
	/**
	 * 错误信息
	 * 
	 */
	public class ErrorMsg {
		/** 业务异常、系统异常 **/
		public static final String MSG00001 = "网络异常，请稍后再试！";
		/** 网络异常 **/
		public static final String MSG00002 = "网络异常，请稍后再试！";
		/** 用户绑定多张银行卡 **/
		public static final String MSG00003 = "您绑定了多张银行卡，请联系客服！";
		
		public static final String MSG00004 = "请选择开户省份";
		public static final String MSG00005 = "请选择开户城市";
		public static final String MSG00006 = "请输入支行名称";
		
		public static final String MSG00007 = "请输入提现金额";
		public static final String MSG00008 = "请输入验证码";
		
		public static final String MSG00009 = "请输入投资金额";
	}
	
	/** 发送标识 */
	public static final String SETUP_FLAG = "2";
	
	/** 
	 * 网站或手机支持（PHONE:手机；WEBSITE:网站；WEBANDPHONE:网站和手机）
	 **/
	public class BANK {
		public static final String PHONE = "PHONE";
		public static final String WEBSITE = "WEBSITE";
		public static final String WEBANDPHONE = "WEBANDPHONE";
	}
	
	/** 
	 * 支持支付类型（0：全部，1：认证支付，2、网银支付）
	 **/
	public class PAYTYPE {
		public static final String ALL = "0";
		public static final String RENZHENG = "1";
		public static final String WANGYIN = "2";
	}
	
	/** 
	 * 显示（1、网站；2、app；3、微站）
	 **/
	public class BANNER {
		public static final String WEB = "1";
		public static final String APP = "2";
		public static final String WX = "3";
	}
	/** 
	 * 显示（201、启动页；202、首页弹出）
	 **/
	public class PICTURE {
		public static final String QIDONGYE = "201";
		public static final String SHOUYETANCHU = "202";
	}
	/** 
	 * 显示（201、top-icon；202、button-icon）
	 **/
	public class ICON {
		public static final String TOP_ICON = "201";
		public static final String BUTTON_ICON = "202";
	}
	
	/** 
	 * 类型：1 网站，2 微站，3 app
	 **/
	public class ACTIVITY {
		public static final String WEB = "1";
		public static final String WX = "2";
		public static final String APP = "3";
	}
}
