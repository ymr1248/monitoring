package com.monitoring.appcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.monitoring.entity.MonitorGroup;
import com.monitoring.entity.Monitoring;
import com.monitoring.entity.User;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.UserService;
import com.monitoring.util.MyObject;
import com.monitoring.util.Pager;
import com.monitoring.util.ResponseUtils;

@Controller
public class AppMonitorGroupController {
	@Autowired
	MonitorgroupService monitorgroupService;
	@Autowired
	UserService userService;
	@Autowired
	MonitoringService monitoringService;
	
	
	//app添加监控器组
	@RequestMapping(value = "/appAddMonitorGroup")
	public void addMonitorGroup(HttpServletRequest request, HttpServletResponse response) {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String groupName = request.getParameter("groupName");
		String groupType = request.getParameter("groupType");
		MonitorGroup monitorGroup = new MonitorGroup();
		User user = userService.getUserById(userId);
		monitorGroup.setUser(user);
		monitorGroup.setGroupName(groupName);
		monitorGroup.setGroupType(groupType);
		int result = monitorgroupService.add(monitorGroup);
		Gson gson = new Gson();
		MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//更新监控器组信息
	@RequestMapping(value = "/appUpdateMonitorGroup")
	public void AppUpdateMonitorGroup(HttpServletRequest request, HttpServletResponse response) {
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		String groupName = request.getParameter("groupName");
		String groupType = request.getParameter("groupType");
		MonitorGroup monitorGroup = new MonitorGroup();
		monitorGroup.setGroupName(groupName);
		monitorGroup.setGroupType(groupType);
		monitorGroup.setGroupId(groupId);
		int result = monitorgroupService.update(monitorGroup);
		Gson gson = new Gson();
		MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//删除监控器组
	@RequestMapping(value="/appMonitorGroupDeleteBoth")
	public void AppMonitorGroupDeleteBoth(int[] groupId,HttpServletRequest request, HttpServletResponse response){
		int userId = Integer.parseInt(request.getParameter("userId"));
		for (int i = 0; i < groupId.length; i++) {
			List<Monitoring> list = monitoringService.getMonitors(userId, groupId[i]);
			for (int j = 0; j < list.size(); j++) {
				monitoringService.delMonitoring(list.get(j).getId());
			}
			monitorgroupService.delete(groupId[i]);
		}
		int result = 1;
		Gson gson = new Gson();
		MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//删除监控器组与组内监控器
	@RequestMapping(value="/appMonitorGroupDeleteOnly")
	public void AppMonitorGroupDeleteOnly(int[] groupId,HttpServletRequest request, HttpServletResponse response){
		int userId = Integer.parseInt(request.getParameter("userId"));
		int defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
		monitoringService.alterGroupId(userId, groupId, defaultGroupId);
		for (int i = 0; i < groupId.length; i++) {
			monitorgroupService.delete(groupId[i]);
		}
		int result = 1;
		Gson gson = new Gson();
		MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//获取监控器组列表
	@RequestMapping(value="/appMonitorGroupList")
	public void AppMonitorGroupList(int userId,int pageNum,HttpServletRequest request, HttpServletResponse response){
		Gson gson = new Gson();
		String data;
		int flag=0;
		if(request.getParameter("search")!=null){
			String groupName=request.getParameter("search");
			List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroups(userId, groupName);
			Pager<MonitorGroup> pager = new Pager<MonitorGroup>(pageNum, 10, monitorGroups);
			data = gson.toJson(pager);
			if(pager.getTotalPage()!=0){
				flag=1;
			}
		}else {
			List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroupsByUserId(userId);
			Pager<MonitorGroup> pager = new Pager<MonitorGroup>(pageNum, 10, monitorGroups);
			data = gson.toJson(pager);
			if(pager.getTotalPage()!=0){
				flag=1;
			}
		}
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	
}
