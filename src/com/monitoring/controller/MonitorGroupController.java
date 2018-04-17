package com.monitoring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.monitoring.entity.MonitorGroup;
import com.monitoring.entity.Monitoring;
import com.monitoring.entity.User;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.service.MonitoringService;
import com.monitoring.util.MyTestUtils;

@Controller
public class MonitorGroupController {

	@Autowired
	MonitorgroupService monitorgroupService;
	@Autowired
    MonitoringService monitoringService;
	
	/*
	 * 前往添加监控器组页面
	 */
	@RequestMapping(value = "/toMonitorgroup_add")
	public String toMonitorgroup_add(HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		return "userviews/monitorgroup_add";
	}

	/*
	 * 添加监控器组
	 * 返回监控组列表页面
	 */
	@RequestMapping(value = "/monitorgroup_add")
	public String monitorgroup_add(MonitorGroup monitorGroup, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		monitorGroup.setUser((User)request.getSession().getAttribute("USER"));
		if(monitorgroupService.add(monitorGroup)!=1){
			modelMap.put("reason", "添加失败！！");
			return "page_400";
		}
		return "redirect:toMonitorgroup_list?pageNum=1";
	}
	
	/*
	 * 通过页码查询数据
	 * search为可选参，用来查询监控组
	 * defaultGroupId为默认组，作用是不让用户删除该组
	 * 返回监控器组列表
	 */
	@RequestMapping(value = "/toMonitorgroup_list")
	public String toMonitorgroup_list(int pageNum, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		int userId = ((User)request.getSession().getAttribute("USER")).getId();
		//默认组   不能删
		int defaultGroupId;
		//如果有搜索则调用下面函数
		if(request.getParameter("search")!=null){
			//获取默认组id
			defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
			String groupName=request.getParameter("search");
			List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroups(userId, groupName);
			modelMap.addAttribute("defaultGroupId", defaultGroupId);
			modelMap.addAttribute("search", groupName);
			modelMap.addAttribute("list", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getDataList());
			modelMap.addAttribute("pageNum",pageNum);
			modelMap.addAttribute("totalPage", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getTotalPage());
			return "userviews/monitorgroup_list";
		}
		defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
		//如果没搜索，直接返回数据
		List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroupsByUserId(userId);
		modelMap.addAttribute("defaultGroupId", defaultGroupId);
		modelMap.addAttribute("list", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getDataList());
		modelMap.addAttribute("pageNum",pageNum);
		modelMap.addAttribute("totalPage", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getTotalPage());
		return "userviews/monitorgroup_list";
	}
	
	/*
	 * 仅删除组
	 * 将监控器组内数据全部转移到默认组，然后删除监控器组
	 * defaultGroupId为默认组，作用是不让用户删除该组
	 * 返回监控器列表
	 */
	@RequestMapping(value = "/monitorgroup_deleteOnly")
	public String monitorgroup_deleteOnly(int[] groupId, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		int userId = ((User)request.getSession().getAttribute("USER")).getId();
		int defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
		monitoringService.alterGroupId(userId, groupId, defaultGroupId);
		for (int i = 0; i < groupId.length; i++) {
			monitorgroupService.delete(groupId[i]);
		}
		modelMap.addAttribute("defaultGroupId", defaultGroupId);
		modelMap.addAttribute("list", monitorgroupService.getMonitorGroups(1,userId).getDataList());
		modelMap.addAttribute("pageNum",1);
		modelMap.addAttribute("totalPage", monitorgroupService.getMonitorGroups(1,userId).getTotalPage());
		return "userviews/monitorgroup_list";
	}
	
	/*
	 * 删除监控器组和组内数据
	 * 先删除组内数据，再删除监控器组
	 * defaultGroupId为默认组，作用是不让用户删除该组
	 * 返回监控器列表
	 */
	@RequestMapping(value = "/monitorgroup_deleteBoth")
	public String monitorgroup_deleteBoth(int[] groupId, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		int userId = ((User)request.getSession().getAttribute("USER")).getId();
		int defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
		for (int i = 0; i < groupId.length; i++) {
			List<Monitoring> list = monitoringService.getMonitors(userId, groupId[i]);
			for (int j = 0; j < list.size(); j++) {
				monitoringService.delMonitoring(list.get(j).getId());
			}
			monitorgroupService.delete(groupId[i]);
		}
		modelMap.addAttribute("defaultGroupId", defaultGroupId);
		modelMap.addAttribute("list", monitorgroupService.getMonitorGroups(1,userId).getDataList());
		modelMap.addAttribute("pageNum",1);
		modelMap.addAttribute("totalPage", monitorgroupService.getMonitorGroups(1,userId).getTotalPage());
		return "userviews/monitorgroup_list";
	}
	
	/*
	 * 前往更新页面
	 */
	@RequestMapping(value = "/toMonitorgroup_update")
	public String toMonitorgroup_update(int id, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		modelMap.addAttribute("list", monitorgroupService.getMonitorGroupById(id));
		return "userviews/monitorgroup_update";
	}
	
	/*
	 * 更新数据
	 * 返回监控器列表页面
	 */
	@RequestMapping(value = "/monitorgroup_update")
	public String monitorgroup_update(MonitorGroup monitorGroup, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		if(monitorgroupService.update(monitorGroup)!=1){
			modelMap.put("reason", "更新失败！！");
			return "page_400";
		}
		return "redirect:toMonitorgroup_list?pageNum=1";
	}
	
}
