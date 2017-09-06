package com.we.util;

public class ActionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errMsg;
	
	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public ActionException(String message) {
		super(message);
	}
	
	public ActionException(String errCode, String errMsg) {
		super("ActionException:[" + errCode + "]" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
}
