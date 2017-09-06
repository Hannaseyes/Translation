package com.we.domain;

import java.io.Serializable;

public class CountUser implements Serializable{

	private static final long serialVersionUID = -6035324739560723345L;

	
	private String id; // 用户ID  (COUNT_USER_SEQ)
    private String openId; // 用户唯一标识
    private String startValue; // 初始值
    private String  balance; // 余额
    private String createTime; // 创建时间
    private String createPer; // 创建人

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getOpenId () {
        return openId;
    }

    public void setOpenId (String openId) {
        this.openId = openId;
    }


	public String getStartValue() {
		return startValue;
	}

	public void setStartValue(String startValue) {
		this.startValue = startValue;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreatePer() {
		return createPer;
	}

	public void setCreatePer (String createPer) {
        this.createPer = createPer;
    }

}
