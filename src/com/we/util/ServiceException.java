package com.we.util;

public class ServiceException extends ActionException {
	
	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errMsg;
	
	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String errCode, String errMsg) {
		super("[" + errCode + "]" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
}
