package com.monitoring.util;


public class MyObject {
	
	private int resource;
	private String jsonString;
	
	public MyObject(int resource,String jsonString){
		this.resource = resource;
		
		jsonString = jsonString.replaceAll("\"", "/\"");
		
		this.jsonString = jsonString;
		
	}
	
	public int getResource() {
		return resource;
	}
	public void setResource(int resource) {
		this.resource = resource;
	}

	public String getJsonString() {
		return jsonString;
	}

	
}
