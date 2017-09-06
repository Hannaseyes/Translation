package com.we.domain;

import java.io.Serializable;

public class CarHome implements Serializable{

	private static final long serialVersionUID = -3292663575713742390L;
	
	private String carModels;//汽车型号
	private String cleanPrice;//裸车价
	private String Price;//指导价
	private String conductPrice;//指导价
	private String gouzhiShui;//购置税价
	private String countPrice;//合计价格
	private String upLicence;//上牌费用
	private String carBoat;//车船使用税
	private String jiaoqiangXian;//交强险
	private String cuxiao;//促销套餐
	private String buyCarTime;//购车时间
	private String buyCarAddress;//购车地点
	private String commercInsurance;//商业保险
	private String slick;//膜
	private String buyer;//购买商家
	
	public String getCarModels() {
		return carModels;
	}
	public void setCarModels(String carModels) {
		this.carModels = carModels;
	}
	public String getCleanPrice() {
		return cleanPrice;
	}
	public void setCleanPrice(String cleanPrice) {
		this.cleanPrice = cleanPrice;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getConductPrice() {
		return conductPrice;
	}
	public void setConductPrice(String conductPrice) {
		this.conductPrice = conductPrice;
	}
	public String getGouzhiShui() {
		return gouzhiShui;
	}
	public void setGouzhiShui(String gouzhiShui) {
		this.gouzhiShui = gouzhiShui;
	}
	public String getCountPrice() {
		return countPrice;
	}
	public void setCountPrice(String countPrice) {
		this.countPrice = countPrice;
	}
	public String getUpLicence() {
		return upLicence;
	}
	public void setUpLicence(String upLicence) {
		this.upLicence = upLicence;
	}
	public String getCarBoat() {
		return carBoat;
	}
	public void setCarBoat(String carBoat) {
		this.carBoat = carBoat;
	}
	public String getJiaoqiangXian() {
		return jiaoqiangXian;
	}
	public void setJiaoqiangXian(String jiaoqiangXian) {
		this.jiaoqiangXian = jiaoqiangXian;
	}
	public String getCuxiao() {
		return cuxiao;
	}
	public void setCuxiao(String cuxiao) {
		this.cuxiao = cuxiao;
	}
	public String getBuyCarTime() {
		return buyCarTime;
	}
	public void setBuyCarTime(String buyCarTime) {
		this.buyCarTime = buyCarTime;
	}
	public String getBuyCarAddress() {
		return buyCarAddress;
	}
	public void setBuyCarAddress(String buyCarAddress) {
		this.buyCarAddress = buyCarAddress;
	}
	
	public String getCommercInsurance() {
		return commercInsurance;
	}
	public void setCommercInsurance(String commercInsurance) {
		this.commercInsurance = commercInsurance;
	}
	public String getSlick() {
		return slick;
	}
	public void setSlick(String slick) {
		this.slick = slick;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	

}
