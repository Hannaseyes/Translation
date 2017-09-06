package com.we.domain;

import java.io.Serializable;

public class CountChildCategory implements Serializable{
	
	private static final long serialVersionUID = -1093040954553064314L;
	
		private String id; // 子分类ID (COUNT_CHILD_CATEGORY_SEQ)
	    private String childCategoryCode; // 子分类code
	    private String childCategoryName; // 子分类名字
	    private String categoryId; // 分类ID

	    public String getId () {
	        return id;
	    }

	    public void setId (String id) {
	        this.id = id;
	    }

	    
	    public String getChildCategoryCode() {
			return childCategoryCode;
		}

		public void setChildCategoryCode(String childCategoryCode) {
			this.childCategoryCode = childCategoryCode;
		}

		public String getChildCategoryName() {
			return childCategoryName;
		}

		public void setChildCategoryName(String childCategoryName) {
			this.childCategoryName = childCategoryName;
		}

		public String getCategoryId () {
	        return categoryId;
	    }

	    public void setCategoryId (String categoryId) {
	        this.categoryId = categoryId;
	    }
}
