package com.monitoring.appcontroller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.gson.Gson;
import com.monitoring.entity.Space;
import com.monitoring.entity.SpaceOrder;
import com.monitoring.service.SpaceOrderService;
import com.monitoring.service.SpaceService;
import com.monitoring.util.MyObject;
import com.monitoring.util.ResponseUtils;

@Controller
public class AppSpaceController {

	@Autowired
	SpaceService spaceService;
	@Autowired 
	SpaceOrderService spaceOrderService;
	
	
	//获取空间
	@RequestMapping(value = "/appGetSpace")
	public void appGetSpace(int userId,HttpServletRequest request, HttpServletResponse response) {
		Space space = spaceService.getSpaceByUserId(userId);
		int flag = 0;
		if(space!=null){
			flag = 1;
		}
		Gson gson = new Gson();
		String data = gson.toJson(space);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}

	//获取空间订单
	@RequestMapping(value = "/appGetSpaceOrderByName")
	public void appGetSpaceOrderByName(HttpServletRequest request, HttpServletResponse response) {
		String name ="";
		if(request.getParameter("name")!=null){
			name = request.getParameter("name");
		}
		List<Space> list = spaceService.getSpaceByNameLike(name);
		List<SpaceOrder> orderList = spaceOrderService.getSpaceOrdersBySpaceId(list);
		int flag = 0;
		if(orderList.size()>0){
			flag = 1;
		}
		Gson gson = new Gson();
		String data = gson.toJson(orderList);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//支付后获取空间信息
	@RequestMapping(value = "/appGetInfoAfterPay")
	public void appGetInfoAfterPay(int userId,HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 这里放空间与金额是否匹配的判断语句，并将数据录入数据库
		 */
		
		Space space = spaceService.getSpaceByUserId(userId);
		int flag = 0;
		if(space!=null){
			flag = 1;
		}
		Gson gson = new Gson();
		String data = gson.toJson(space);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	
	
	
}
