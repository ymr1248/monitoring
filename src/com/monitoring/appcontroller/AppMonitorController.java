package com.monitoring.appcontroller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.monitoring.entity.Monitoring;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.UserService;
import com.monitoring.util.MyObject;
import com.monitoring.util.Pager;
import com.monitoring.util.ResponseUtils;

@Controller
public class AppMonitorController {

	@Autowired
	UserService userService;
	@Autowired
	MonitorgroupService monitorgroupService;
	@Autowired
	MonitoringService monitoringService;
	
	//添加监控器
	@RequestMapping(value = "/appAddMonitor")
	public void AppAddMonitor(HttpServletRequest request, HttpServletResponse response) {
		
        Monitoring monitoring = new Monitoring();
        String monitorInfo = request.getParameter("monitorInfo");
        String monitorIp = request.getParameter("monitorIp");
        String monitorLocation = request.getParameter("monitorLocation");
        String monitorName = request.getParameter("monitorName");
        int monitorStatus = Integer.parseInt(request.getParameter("monitorStatus"));
        String monitorType = request.getParameter("monitorType");
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        int userId =Integer.parseInt(request.getParameter("userId"));
        String storageLocation = request.getParameter("storageLocation");
       
        monitoring.setMonitorInfo(monitorInfo);
        monitoring.setMonitorIp(monitorIp);
        monitoring.setMonitorLocation(monitorLocation);
        monitoring.setMonitorName(monitorName);
        monitoring.setMonitorStatus(monitorStatus);
        monitoring.setMonitorType(monitorType);
        monitoring.setMonitorGroup(monitorgroupService.getMonitorGroupById(groupId));
        monitoring.setUser(userService.getUserById(userId));
        monitoring.setStorageLocation(storageLocation);
        monitoring.setLocatedTime(new Date());
        
        int result = monitoringService.add(monitoring);
        Gson gson = new Gson();
		MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}

	//删除监控器
	@RequestMapping(value = "/appDeleteMonitor")
	public void AppDeleteMonitor(int id,HttpServletRequest request, HttpServletResponse response) {
		int result;
		if(monitoringService.delMonitoring(id)){
			result=1;
		}else {
			result=0;
		}
		Gson gson = new Gson();
		MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//更新监控器
	@RequestMapping(value = "/appUpdateMonitor")
	public void AppUpdateMonitor(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String monitorInfo = request.getParameter("monitorInfo");
        String monitorIp = request.getParameter("monitorIp");
        String monitorLocation = request.getParameter("monitorLocation");
        String monitorName = request.getParameter("monitorName");
        int monitorStatus = Integer.parseInt(request.getParameter("monitorStatus"));
        String monitorType = request.getParameter("monitorType");
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        String storageLocation = request.getParameter("storageLocation");
        Monitoring monitoring = new Monitoring();
        monitoring.setMonitorIp(monitorIp);
        monitoring.setMonitorName(monitorName);
        monitoring.setMonitorInfo(monitorInfo);
        monitoring.setMonitorLocation(monitorLocation);
        monitoring.setMonitorStatus(monitorStatus);
        monitoring.setStorageLocation(storageLocation);
        monitoring.setMonitorGroup(monitorgroupService.getMonitorGroupById(groupId));
        monitoring.setId(id);
        monitoring.setMonitorType(monitorType);
        int result;
        if (monitoringService.updateMonitorByAddition(monitoring)){
        	result=1;
        }else{
        	result=0;
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//查询监控器组的监控器
	@RequestMapping(value = "/appMonitorList")
	public void AppMonitorList(HttpServletRequest request, HttpServletResponse response) {
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	int groupId = Integer.parseInt(request.getParameter("groupId"));
    	int pageNum = Integer.parseInt(request.getParameter("pageNum"));
    	Gson gson = new Gson();
    	String data;
    	int flag=0;
    	if(request.getParameter("search")!=null){
    		String monitorName=request.getParameter("search");
    		List<Monitoring> monitors = monitoringService.getMonitors(userId,groupId,monitorName);
    		Pager<Monitoring> pager = new Pager<Monitoring>(pageNum, 10, monitors);
    		data = gson.toJson(pager);
			if(pager.getTotalPage()!=0){
				flag=1;
			}
    		
    	}else {
    		List<Monitoring> monitors = monitoringService.getMonitors(userId, groupId);
    		Pager<Monitoring> pager = new Pager<Monitoring>(pageNum, 10, monitors);
    		data = gson.toJson(pager);
			if(pager.getTotalPage()!=0){
				flag=1;
			}
		}
    	MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//监控器详细信息
	@RequestMapping(value = "/appMonitor")
	public void AppMonitor(int id,HttpServletRequest request, HttpServletResponse response) {
		String data;
		Gson gson = new Gson();
		Monitoring monitoring = null;
		int flag = 0;
		if(monitoringService.getMonitoringById(id)!=null){
			monitoring = monitoringService.getMonitoringById(id);
			flag = 1;
		}
		data = gson.toJson(monitoring);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//删除多个监控器
	@RequestMapping(value = "/appDeleteMonitors")
	public void AppDeleteMonitors(int id[],HttpServletRequest request, HttpServletResponse response) {
		int flag = id.length;
		for (int i=0; i<id.length;i++) {
		   if(monitoringService.delMonitoring(id[i])){
			   flag=flag-1;
		   }
		}
		Gson gson = new Gson();
		if(flag!=1){
			flag = 0;
		}
		MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);	
	}
	
	//移动多个监控器
	@RequestMapping(value = "/appRemoveMonitors")
	public void AppRemoveMonitors(int id[],int groupId,HttpServletRequest request, HttpServletResponse response) {
		int flag = id.length;
		for (int i=0; i<id.length;i++) {
		   if(monitoringService.alterGroupId(id[i],groupId)==1){
			   flag=flag-1;
		   }
		}
		Gson gson = new Gson();
		if(flag!=1){
			flag = 0;
		}
		MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);				
	}
	
	@RequestMapping(value = "/appGetMonitorByName")
	public void appGetMonitorByName(int userId,String monitorName,HttpServletRequest request, HttpServletResponse response) {
		List<Monitoring> monitors = monitoringService.getMonitors(userId, monitorName);
		int flag = -1;
		if(monitors.size()==0){
			flag = 0;
		}
		if(monitors.size()>0){
			flag = 1;
		}
		Gson gson = new Gson();
		String data = gson.toJson(monitors);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	
	
}
