package com.we.domain;

/**
 * 品牌实体类
 * @author Wang Hanqing
 */
public class Brand {

    private String id;//品牌ID
    private String brandName;//品牌名称
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
