package com.monitoring.util;

import javax.servlet.http.HttpServletRequest;

public class MyTestUtils {
	public static String isLogined(HttpServletRequest request){
		if(request.getSession().getAttribute("MANAGER")==null){
			return "page_403";
		}
		return null;
	}

	public static String userIsLogined(HttpServletRequest request){
		if(request.getSession().getAttribute("USER")==null){
			return "page_403";
		}
		return null;
	}

	public static String operatorIsLogined(HttpServletRequest request){
		if(request.getSession().getAttribute("OPERATOR")==null){
			return "page_403";
		}
		return null;
	}
}
