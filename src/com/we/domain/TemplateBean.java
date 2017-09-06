package com.we.domain;

import java.util.List;  

public class TemplateBean {  
	
	public static class TTemplateParamList{

		private	String	color;	/*#173177*/
		private	String	name;	/*keyword1*/
		private	String	value;	/*339208499*/

		public void setColor(String value){
			this.color = value;
		}
		public String getColor(){
			return this.color;
		}

		public void setName(String value){
			this.name = value;
		}
		public String getName(){
			return this.name;
		}

		public void setValue(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}

	}
	private	List<TTemplateParamList>	templateParamList;	/*List<TTemplateParamList>*/
	public void setTemplateParamList(List<TTemplateParamList> value){
		this.templateParamList = value;
	}
	public List<TTemplateParamList> getTemplateParamList(){
		return this.templateParamList;
	}

	private	String	templateId;	/*SL8OdGq4e4-665Xkx6p9Yq5h5N6vhIScnUn3y8JU2ag*/
	private	String	toUser;	/*that.data.openId*/
	private	String	formId;	/*formId*/
	private	String	topColor;	/*#ffffff*/
	private	String	url;	/*huiLv*/
	private	String	emphasis_keyword;	/*keyword1.DATA*/

	public void setTemplateId(String value){
		this.templateId = value;
	}
	public String getTemplateId(){
		return this.templateId;
	}

	public void setToUser(String value){
		this.toUser = value;
	}
	public String getToUser(){
		return this.toUser;
	}

	public void setFormId(String value){
		this.formId = value;
	}
	public String getFormId(){
		return this.formId;
	}

	public void setTopColor(String value){
		this.topColor = value;
	}
	public String getTopColor(){
		return this.topColor;
	}

	public void setUrl(String value){
		this.url = value;
	}
	public String getUrl(){
		return this.url;
	}

	public void setEmphasis_keyword(String value){
		this.emphasis_keyword = value;
	}
	public String getEmphasis_keyword(){
		return this.emphasis_keyword;
	}
    public String toJSON() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("{");  
        buffer.append(String.format("\"touser\":\"%s\"", this.toUser)).append(",");  
        buffer.append(String.format("\"template_id\":\"%s\"", this.templateId)).append(",");  
        buffer.append(String.format("\"page\":\"%s\"", this.url)).append(",");  
        buffer.append(String.format("\"form_id\":\"%s\"", this.formId)).append(",");  
        buffer.append(String.format("\"topcolor\":\"%s\"", this.topColor)).append(",");  
        buffer.append("\"data\":{");  
        TTemplateParamList param = null;  
        for (int i = 0; i < this.templateParamList.size(); i++) {  
             param = templateParamList.get(i);  
            // 判断是否追加逗号  
            if (i < this.templateParamList.size() - 1){  
                  
                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"},", param.getName(), param.getValue(), param.getColor()));  
            }else{  
                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"}", param.getName(), param.getValue(), param.getColor()));  
            }  
          
        }  
        buffer.append("}");  
        buffer.append("}");  
        return buffer.toString();  
        }  
  
}  
