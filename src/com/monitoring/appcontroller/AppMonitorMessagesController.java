package com.monitoring.appcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.monitoring.entity.MonitorMessages;
import com.monitoring.service.MonitorMessageService;
import com.monitoring.util.MyObject;
import com.monitoring.util.ResponseUtils;

@Controller
public class AppMonitorMessagesController {

	@Autowired
	MonitorMessageService monitorMessageService;
	
	//通过id查询监控器消息
	@RequestMapping(value = "/appGetMonitorMessageById")
	public void appGetMonitorMessageById(int id,HttpServletRequest request, HttpServletResponse response) {
		MonitorMessages monitorMessage = monitorMessageService.getMonitorMessageById(id);
		int flag = 0;
		if(monitorMessage!=null){
			flag = 1;
		}
		Gson gson = new Gson();
		String data = gson.toJson(monitorMessage);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//通过监控器id获取监控器消息列表
	@RequestMapping(value = "/appGetMonitorMessagesByMonitorId")
	public void appGetMonitorMessagesByMonitorId(int monitorId,HttpServletRequest request, HttpServletResponse response) {
		List<MonitorMessages> list = monitorMessageService.getMonitorMessagesByMonitorId(monitorId);
		int flag=-1;
		if(list.size()==0){
			flag = 0;
		}
		if(list.size()>0){
			flag = 1;
		}
		Gson gson = new Gson();
		String data = gson.toJson(list);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//通过id删除监控器消息
	@RequestMapping(value = "/appDeleteMonitorMessageById")
	public void appDeleteMonitorMessageById(int id,HttpServletRequest request, HttpServletResponse response) {
		int flag = monitorMessageService.delete(id);
		Gson gson = new Gson();
		MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//通过数组id删除监控器消息列表
	@RequestMapping(value = "/appDeleteMonitorMessagesByMonitorId")
	public void appDeleteMonitorMessagesByMonitorId(String id,HttpServletRequest request, HttpServletResponse response) {
		String s[]=id.split(",");
		int ids[]=new int[s.length];
		for(int i=0; i<s.length; i++){
			ids[i] = Integer.parseInt(s[i]);
		}
		int result = monitorMessageService.delete(ids);
		Gson gson = new Gson();
		MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
}
