package com.xy.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Scale {
	private String type;
	
	private String width;
	
	private String height;
	
	private String rate;
	
	private String imgData;
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
	
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public void setImgData(String imgData) {
		this.imgData = imgData;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getWidth() {
		return this.width;
	}
	
	public String getHeight() {
		return this.height;
	}
	
	public String getRate() {
		return this.rate;
	}
	
	public String getImageData() {
		return this.imgData;
	}
	@Override
	public String toString() {
		return "ScaleInfo: {"
				+ " type=" + this.type
				+ " width=" + this.width
				+ " height=" + this.height
				+ " rate=" + this.rate
				+ " imageData=" + this.imgData
				+ "}";
	}
}
