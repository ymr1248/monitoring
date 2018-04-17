package com.monitoring.controller;

import com.monitoring.entity.MonitorGroup;
import com.monitoring.entity.Monitoring;
import com.monitoring.entity.User;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.UserService;
import com.monitoring.util.MyTestUtils;
import com.monitoring.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class MonitoringController {
    @Autowired
    UserService userService;
    @Autowired
    MonitoringService monitoringService;
    @Autowired
    MonitorgroupService monitorgroupService;

    /**
     * 跳转到添加监控器
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAddMonitor")
    public String toAddMonitor(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
        	int userId = ((User)request.getSession().getAttribute("USER")).getId();
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroupsByUserId(userId);
            modelMap.addAttribute("monitorGroups",monitorGroups);
            modelMap.addAttribute("groupId",groupId);
            return "userviews/add_monitor";
        }
    }

    /**
     * 添加监控器
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addMonitor")
    public String addMonitor(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            Monitoring monitoring = new Monitoring();

            String monitorName = request.getParameter("monitorName");
            String monitorIp = request.getParameter("monitorIp");
            String monitorLocation = request.getParameter("monitorLocation");
            String monitorType = request.getParameter("monitorType");
            int userId = ((User)request.getSession().getAttribute("USER")).getId();
            Date locatedTime = new Date();
            int monitorStatus = Integer.parseInt(request.getParameter("monitorStatus"));
            String monitorInfo = request.getParameter("monitorInfo");
            String storageLocation = request.getParameter("storageLocation");
            int groupId = Integer.parseInt(request.getParameter("groupId"));

            monitoring.setMonitorIp(monitorIp);
            monitoring.setMonitorLocation(monitorLocation);
            monitoring.setMonitorName(monitorName);
            monitoring.setMonitorInfo(monitorInfo);
            monitoring.setLocatedTime(locatedTime);
            monitoring.setMonitorType(monitorType);
            monitoring.setUser(userService.getUserById(userId));
            monitoring.setMonitorStatus(monitorStatus);
            monitoring.setStorageLocation(storageLocation);
            monitoring.setMonitorGroup(monitorgroupService.getMonitorGroupById(groupId));
            if (monitoringService.add(monitoring) != 1) {
                modelMap.put("reason", "添加失败！！");
                return "page_400";
            }
            return "redirect:toMonitor_list?pageNum=1&groupId="+groupId;
        }

    }

    /**
     * 用户删除名下监控器
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/deleteMonitor")
    public String deleteMonitor(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            int monitorId = Integer.parseInt(request.getParameter("monitorId"));
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            if(!monitoringService.delMonitoring(monitorId)){
                modelMap.put("reason","删除失败请重试！！！");
                return "page_400";
            }
            return "redirect:toMonitor_list?pageNum=1&groupId="+groupId;
        }
    }

    /**
     * 跳转到更新页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUpdateMonitor")
    public String toUpdateMonitor(HttpServletRequest request,ModelMap modelMap){
        if(MyTestUtils.userIsLogined(request) != null){
            return"page_403";
        }else{
            int monitorId = Integer.parseInt(request.getParameter("monitorId"));
            int userId = ((User)request.getSession().getAttribute("USER")).getId();
            List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroupsByUserId(userId);
            Monitoring monitor = monitoringService.getMonitoringById(monitorId);
            modelMap.addAttribute("monitor",monitor);
            modelMap.addAttribute("monitorGroups",monitorGroups);
            return "userviews/monitor_update";
        }
    }

    /**
     * 更新摄像头信息
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateMonitor")
    public String updateMonitor(HttpServletRequest request,ModelMap modelMap){
        if(MyTestUtils.userIsLogined(request) != null){
            return"page_403";
        }else {
            Monitoring monitoring = new Monitoring();
            int monitorId = Integer.parseInt(request.getParameter("monitorId"));
            String monitorIp = request.getParameter("monitorIp");
            String monitorName = request.getParameter("monitorName");
            String monitorInfo = request.getParameter("monitorInfo");
            String monitorLocation = request.getParameter("monitorLocation");
            int monitorStatus  = Integer.parseInt(request.getParameter("monitorStatus"));
            String monitorType  = request.getParameter("monitorType");
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            String storageLocation = request.getParameter("storageLocation");

            monitoring.setMonitorIp(monitorIp);
            monitoring.setMonitorName(monitorName);
            monitoring.setMonitorInfo(monitorInfo);
            monitoring.setMonitorLocation(monitorLocation);
            monitoring.setMonitorStatus(monitorStatus);
            monitoring.setStorageLocation(storageLocation);
            monitoring.setMonitorGroup(monitorgroupService.getMonitorGroupById(groupId));
            monitoring.setId(monitorId);
            monitoring.setMonitorType(monitorType);

            if (monitoringService.updateMonitorByAddition(monitoring)){
                
                return "redirect:toMonitor_info?id="+monitorId;
            }else{
                modelMap.put("reason","更新出错请重新输入！！！");
                return "page_400";
            }
        }
    }
    
    /*
     * 前往监控器列表，与批量操作的监控器列表不一样
     * search为可选参
     * 返回监控器列表
     */
    @RequestMapping(value = "/toMonitor_list")
    public String toMonitor_list(int groupId,int pageNum,HttpServletRequest request, ModelMap modelMap) {
    	if(MyTestUtils.userIsLogined(request) != null){
            return"page_403";
        }
    	int userId = ((User)request.getSession().getAttribute("USER")).getId();
    	if(request.getParameter("search")!=null){
    		String monitorName=request.getParameter("search");
    		List<Monitoring> monitors = monitoringService.getMonitors(userId,groupId,monitorName);
    		Pager<Monitoring> pager = monitoringService.getMonitors(monitors, pageNum);
    		modelMap.addAttribute("search", monitorName);
    		modelMap.addAttribute("pageNum", pageNum);
        	modelMap.addAttribute("totalPage", pager.getTotalPage());
        	modelMap.addAttribute("groupId", groupId);
        	modelMap.addAttribute("monitorList", pager.getDataList());
        	return "userviews/monitor_list";
    	}
    	List<Monitoring> monitors = monitoringService.getMonitors(userId, groupId);
    	Pager<Monitoring> pager = monitoringService.getMonitors(monitors, pageNum);
    	modelMap.addAttribute("monitorList", pager.getDataList());
    	modelMap.addAttribute("pageNum", pageNum);
    	modelMap.addAttribute("totalPage", pager.getTotalPage());
    	modelMap.addAttribute("groupId", groupId);
    	return "userviews/monitor_list";
    }
    
    /*
     * 前往批量操作的监控器列表页面
     * search为可选参，用于模糊查询监控器名称
     * 返回批量操作的监控器列表
     */
    @RequestMapping(value = "/toMonitor_list2")
    public String toMonitor_list2(int groupId,int pageNum,HttpServletRequest request, ModelMap modelMap) {
    	if(MyTestUtils.userIsLogined(request) != null){
            return"page_403";
        }
    	int userId = ((User)request.getSession().getAttribute("USER")).getId();
    	List<MonitorGroup> list = monitorgroupService.getMonitorGroupsByUserId(userId);
    	if(request.getParameter("search")!=null){
    		String monitorName=request.getParameter("search");
    		List<Monitoring> monitors = monitoringService.getMonitors(userId,groupId,monitorName);
    		Pager<Monitoring> pager = monitoringService.getMonitors(monitors, pageNum);
    		modelMap.addAttribute("monitorGroup", list);
    		modelMap.addAttribute("search", monitorName);
    		modelMap.addAttribute("pageNum", pageNum);
        	modelMap.addAttribute("totalPage", pager.getTotalPage());
        	modelMap.addAttribute("groupId", groupId);
        	modelMap.addAttribute("monitorList", pager.getDataList());
        	return "userviews/monitor_list2";
    	}
    	List<Monitoring> monitors = monitoringService.getMonitors(userId, groupId);
    	Pager<Monitoring> pager = monitoringService.getMonitors(monitors, pageNum);
    	modelMap.addAttribute("monitorGroup", list);
    	modelMap.addAttribute("monitorList", pager.getDataList());
    	modelMap.addAttribute("pageNum", pageNum);
    	modelMap.addAttribute("totalPage", pager.getTotalPage());
    	modelMap.addAttribute("groupId", groupId);
    	return "userviews/monitor_list2";
    }
    
    /*
     * 前往监控器详细信息页面
     */
    @RequestMapping(value = "/toMonitor_info")
    public String toMonitor_info(int id,HttpServletRequest request, ModelMap modelMap) {
    	Monitoring monitoring = monitoringService.getMonitoringById(id);
    	modelMap.addAttribute("monitoring", monitoring);
        return "userviews/monitor_info";
    }
    
    /*
     * 批量删除监控器
     * 返回监控器组列表
     */
    @RequestMapping(value = "/monitor_deleteBoth")
    public String monitor_deleteBoth(int[] id,HttpServletRequest request, ModelMap modelMap) {
    	for(int i=0;i<id.length;i++){
    		monitoringService.delMonitoring(id[i]);
    	}
        return "redirect:toMonitorgroup_list?pageNum=1";
    }
    
    /*
     * 批量移动监控器
     * 返回监控器组列表页面
     */
    @RequestMapping(value = "/monitor_removeBoth")
    public String monitor_removeBoth(int[] id,int groupId,HttpServletRequest request, ModelMap modelMap) {
    	for(int i=0;i<id.length;i++){
    		monitoringService.alterGroupId(id[i], groupId);
    	}
        return "redirect:toMonitorgroup_list?pageNum=1";
    }
    
    
}
