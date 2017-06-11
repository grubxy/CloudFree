package com.xy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultModel {
	
	private String url;
	
	private String errCode;
	
	private String errMsg;
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setErrCode(String errCode) {	
		this.errCode = errCode;
	}
	
	public void setErrMsg(String errMsg) {	
		this.errMsg = errMsg;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getErrCode() {
		return errCode;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	@Override
	public String toString() {
		return "Results {" 
				+ " url=" + this.url
				+ " errCode=" +this.errCode
				+ " errMsg=" + this.errMsg
				+ "}";
	}
}
