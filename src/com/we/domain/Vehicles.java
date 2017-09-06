package com.we.domain;

/**
 * 车系实体类
 * @author Wang Hanqing
 */
public class Vehicles {
    private String id;//车系id
    private String vehiclesName;//车名
    private String brandName;//所属品牌名称
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getVehiclesName() {
        return vehiclesName;
    }
    public void setVehiclesName(String vehiclesName) {
        this.vehiclesName = vehiclesName;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
