package com.we.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class CountDetails implements Serializable{
	
	private static final long serialVersionUID = -1359640599454035534L;
	
	    private String id; // 明细ID(COUNT_DETAILS_SEQ)
	    private String openId; // 用户唯一标识
	    private String category ; // 大分类
	    private String childCategory; // 小分类
	    private String money; // 金额
	    private String payment; // 支付方式
	    private String source; // 收入来源
	    private String remarks; // 备注
	    private String status; // 收支标识（1.收入，2.支出）
	    private String createTime; // 创建时间
	    private String createPer; // 创建人
	    private String showTime;//前端显示时间

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

	    public String getCategory  () {
	        return category ;
	    }

	    public void setCategory  (String category ) {
	        this.category  = category ;
	    }

	    public String getChildCategory () {
	        return childCategory;
	    }

	    public void setChildCategory (String childCategory) {
	        this.childCategory = childCategory;
	    }

	    public String getPayment () {
	        return payment;
	    }

	    public void setPayment (String payment) {
	        this.payment = payment;
	    }

	    public String getSource () {
	        return source;
	    }

	    public void setSource (String source) {
	        this.source = source;
	    }

	    public String getRemarks () {
	        return remarks;
	    }

	    public void setRemarks (String remarks) {
	        this.remarks = remarks;
	    }

	    public String getStatus () {
	        return status;
	    }

	    public void setStatus (String status) {
	        this.status = status;
	    }

	    public String getCreatePer () {
	        return createPer;
	    }

	    public void setCreatePer (String createPer) {
	        this.createPer = createPer;
	    }

		public String getMoney() {
			return money;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getShowTime() {
			return showTime;
		}

		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}



}
