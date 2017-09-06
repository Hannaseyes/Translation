package com.we.domain;

import java.io.Serializable;

public class CountCategory implements Serializable{
	
	private static final long serialVersionUID = 8414954229124999475L;
	
	private String id; // 分类ID(COUNT_CATEGORY_SEQ)
    private String categoryName; // 分类名称
    private String categoryCode; // 分类code

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode () {
        return categoryCode;
    }

    public void setCategoryCode (String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
