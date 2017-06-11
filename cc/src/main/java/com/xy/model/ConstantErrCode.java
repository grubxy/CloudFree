package com.xy.model;

public enum ConstantErrCode {

	OK("000000", "成功"),
	
	IMG_ENGINE_ERROR("x000001", "图片处理引擎失败"),
	
	STORAGE_ERROR("x000002", "图片存储失败");
	
	private String code;
	
	private String desc;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	private ConstantErrCode() {	
		this.setCode("000000");
		this.setDesc("成功");
	}
	
	private ConstantErrCode(String code, String desc) {		
		this.setCode(code);
		this.setDesc(desc);	
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
