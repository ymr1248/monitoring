package com.monitoring.controller;

import com.monitoring.entity.*;
import com.monitoring.service.*;
import com.monitoring.util.CyptoUtils;
import com.monitoring.util.MyTestUtils;
import com.monitoring.util.Pager;
import com.monitoring.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@SessionAttributes(value = "operator")
@Controller
public class OperatorController {
    @Autowired
    UserService userService;
    @Autowired
    UserGradeService userGradeService;
    @Autowired
    MonitoringService monitoringService;
    @Autowired
    UserRelationService userRelationService;
    @Autowired
    OperatorService operatorService;
    @Autowired
    VideoService videoService;
    @Autowired
    MonitorgroupService monitorgroupService;


    /**
     * 查看实时直播
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toShowLive")
    public String toShowLive(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            int operatorId = Integer.parseInt(CyptoUtils.decode(request.getParameter("operatorId")));
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            Pager<Video> videoPager = operatorService.getVideoByPageNumAndOperatorId(pageNum, operatorId);
            modelMap.addAttribute("list", videoPager.getDataList());
            modelMap.addAttribute("pageNum", pageNum);
            modelMap.addAttribute("totalPage", videoPager.getTotalPage());
            return "op_show_live";
        }
    }


    /**
     * 高级权限管理低级权限
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/higherOperatorManageLower")
    public String higherOperatorManageLower(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Operator operator = (Operator) request.getSession().getAttribute("OPERATOR");
            int operatorId = operator.getOperatorId();
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            Pager<Operator> operatorPager = operatorService.getOperatorByHigherPermissionId(pageNum, operatorId);
            modelMap.addAttribute("list", operatorPager.getDataList());
            modelMap.addAttribute("totalPage", operatorPager.getTotalPage());
            modelMap.addAttribute("pageNum", pageNum);
            modelMap.addAttribute("mOperator",operator);
            return "opviews/higher_lower_operator";
        }
    }

    /**
     * 跳转到高级操作员添加低级操作员
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAddLowerOperator")
    public String toAddLowerOperator(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Operator operator = (Operator)request.getSession().getAttribute("OPERATOR");
            Permission permission = operator.getPermission();
            modelMap.addAttribute("myPermission", permission);
            return "opviews/lower_operator_add";
        }
    }


    /**
     * 添加低权限操作员
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addLowerOperator")
    public String addLowerOperator(HttpServletRequest request,ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Operator mOperator = (Operator)request.getSession().getAttribute("OPERATOR");
            User user = userRelationService.getUserRelationBySonId(mOperator.getOperatorId()).getUser();

            String operatorName = request.getParameter("operatorName");
            String password = request.getParameter("password");
            String operatorAccount = request.getParameter("operatorAccount");
            String operatorAddress = request.getParameter("operatorAddress");
            String operatorPhone = request.getParameter("operatorPhone");
            Permission permission = Permission.valueOf(request.getParameter("permission"));
            Operator operatorCheck = operatorService.getOperatorByAccount(operatorAccount);
            if (operatorCheck != null) {
                modelMap.put("reason", "已存在该账号，请重新输入账号！");
                return "page_400";
            } else if (operatorService.checkPhone(operatorPhone)) {
                Operator operator = new Operator();
                operator.setOperatorName(operatorName);
                operator.setOperatorAccount(operatorAccount);
                operator.setOperatorAddress(operatorAddress);
                operator.setOperatorPhone(operatorPhone);
                operator.setPassword(password);
                operator.setPermission(permission);
                operator.setApplyState(-1);
                operatorService.addOperator(operator);

                //添加客户和供应商关系
                UserRelation userRelation = new UserRelation();
                userRelation.setUser(user);
                userRelation.setOperator(operatorService.getOperatorByAccount(operatorAccount));

                userRelationService.addUserRelation(userRelation);

                int pageNum = 1;
                return "redirect:/higherOperatorManageLower?pageNum=" + pageNum;
            } else {
                modelMap.put("reason", "电话号码已注册，请重新输入账号！");
                return "page_400";
            }
        }
    }

    /**
     * 转到更新低权限操作员信息页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toLowerOperatorUpdate")
    public String toLowerOperatorUpdate(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Operator operator = (Operator)request.getSession().getAttribute("OPERATOR");
            Permission permission = operator.getPermission();
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            int operatorId = Integer.parseInt(request.getParameter("operatorId"));
            Operator mOperator = operatorService.getOperatorById(operatorId);

            modelMap.addAttribute("mOperator", mOperator);
            modelMap.addAttribute("myPermission", permission);
            modelMap.addAttribute("pageNum",pageNum);
            return "opviews/lower_operator_update";
        }
    }
    /**
     * 对低级权限的操作员做信息更新
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateLowerOperator")
    public String updateLowerOperator(HttpServletRequest request,ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            int operatorId = Integer.parseInt(request.getParameter("operatorId"));
            String operatorName = request.getParameter("operatorName");
            String password = request.getParameter("password");
            String operatorAddress = request.getParameter("operatorAddress");
            String operatorPhone = request.getParameter("operatorPhone");
            Permission permission = Permission.valueOf(request.getParameter("permission"));

            Operator operator = new Operator();
            operator.setOperatorName(operatorName);
            operator.setOperatorAddress(operatorAddress);
            operator.setOperatorPhone(operatorPhone);
            operator.setPassword(password);
            operator.setPermission(permission);
            operator.setOperatorId(operatorId);
            operatorService.update(operator);

            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            return "redirect:/higherOperatorManageLower?pageNum=" + pageNum;

        }
    }

    /**
     * 删除低级操作员
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/deleteLowerOperator")
    public String deleteLowerOperator(HttpServletRequest request,ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            int operatorId = Integer.parseInt(request.getParameter("operatorId"));
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            if (userRelationService.deleteUserRelationBySonId(operatorId)) {
                if (operatorService.deleteOperatorById(operatorId)) {
                    return "redirect:/higherOperatorManageLower?pageNum=" + pageNum;
                }
                return "page_400";
            } else {
                return "page_400";
            }
        }
    }

    /**
     * 管理下级用户申请
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value =  "manageApplyPermission")
    public String manageApplyPermission(HttpServletRequest request,ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Operator operator = (Operator) request.getSession().getAttribute("OPERATOR");
            int operatorId = operator.getOperatorId();
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            Pager<Operator> operatorPager = operatorService.getOperatorByApplyState(pageNum,operatorId,operator.getPermission().ordinal());
            modelMap.addAttribute("list", operatorPager.getDataList());
            modelMap.addAttribute("totalPage", operatorPager.getTotalPage());
            modelMap.addAttribute("pageNum", pageNum);
            modelMap.addAttribute("mOperator",operator);
            return "opviews/manage_apply_permission";
        }
    }

    /**
     * 转到申请权限页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toApplyForPermission")
    public String toApplyForPermission(HttpServletRequest request,ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Operator operator = (Operator)request.getSession().getAttribute("OPERATOR");
            int myPermissionOrdinal = operator.getPermission().ordinal();
            modelMap.addAttribute("myPermissionOrdinal",myPermissionOrdinal);
            return "opviews/apply_for_permission";
        }
    }

    /**
     * 申请权限
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/applyForPermission")
    public String applyForPermission(HttpServletRequest request,ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Operator operator = (Operator) request.getSession().getAttribute("OPERATOR");
            int operatorId = operator.getOperatorId();
            Permission permission = Permission.valueOf(request.getParameter("permission"));
            int permissionOrdinal = permission.ordinal();
            if(operatorService.updateApplyState(operatorId,permissionOrdinal)){
                return "opviews/op_index";
            }else {
                modelMap.put("reason","操作失败请重试！！！！");
                return "page_400";
            }
        }
    }

    /**
     * 同意申请权限
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/agreeApplyPermission")
    public String agreeApplyPermission(HttpServletRequest request, ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            int operatorId = Integer.parseInt(request.getParameter("operatorId"));

            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            if(operatorService.agreePermissionTime(operatorId)){
                return "redirect:/manageApplyPermission?pageNum=" + pageNum;
            }else {
                modelMap.put("reason","操作失败请重试！！！");
                return "page_400";
            }
        }
    }

    /**
     * 不同意通过申请
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/disagreeApplyPermission")
    public String disagreeApplyPermission(HttpServletRequest request,ModelMap modelMap){
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            int operatorId = Integer.parseInt(request.getParameter("operatorId"));
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            if(operatorService.updateApplyState(operatorId,-1)){
                return "redirect:/manageApplyPermission?pageNum=" + pageNum;
            }else {
                modelMap.put("reason","操作失败请重试！！");
                return "page_403";
            }
        }
    }
    
    
    
    
   /*华丽的分割线**********************************************************************************/
   
    /*
	 * 仅删除组
	 * 将监控器组内数据全部转移到默认组，然后删除监控器组
	 * defaultGroupId为默认组，作用是不让用户删除该组
	 * 返回监控器列表
	 */
  	@RequestMapping(value = "/operator_monitorgroup_deleteOnly")
  	public String operator_monitorgroup_deleteOnly(int[] groupId, HttpServletRequest request, ModelMap modelMap) {
  		if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
  		int operatorId =((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
  		int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
  		int defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
  		monitoringService.alterGroupId(userId, groupId, defaultGroupId);
  		for (int i = 0; i < groupId.length; i++) {
  			monitorgroupService.delete(groupId[i]);
  		}
  		modelMap.addAttribute("defaultGroupId", defaultGroupId);
  		modelMap.addAttribute("list", monitorgroupService.getMonitorGroups(1,userId).getDataList());
  		modelMap.addAttribute("pageNum",1);
  		modelMap.addAttribute("totalPage", monitorgroupService.getMonitorGroups(1,userId).getTotalPage());
  		return "opviews/operator_monitorGroup_list";
  	}
  	
  	/*
	 * 删除监控器组和组内数据
	 * 先删除组内数据，再删除监控器组
	 * defaultGroupId为默认组，作用是不让用户删除该组
	 * 返回监控器列表
	 */
  	@RequestMapping(value = "/operator_monitorgroup_deleteBoth")
  	public String operator_monitorgroup_deleteBoth(int[] groupId,HttpServletRequest request, ModelMap modelMap) {
  		if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
  		int operatorId =((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
  		int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
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
  		return "opviews/operator_monitorGroup_list";
  	}
  	
  	
	/*
	 * 前往更新页面
	 */
	@RequestMapping(value = "/toOperatorMonitorgroup_update")
	public String toOperatorMonitorgroup_update(int id, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
		modelMap.addAttribute("list", monitorgroupService.getMonitorGroupById(id));
		return "opviews/operator_monitorgroup_update";
	}
	
	/*
	 * 更新数据
	 * 返回监控器组列表
	 */
	@RequestMapping(value = "/operator_Monitorgroup_update")
	public String operator_Monitorgroup_update(MonitorGroup monitorGroup, HttpServletRequest request, ModelMap modelMap) {
		if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
		if(monitorgroupService.update(monitorGroup)!=1){
			modelMap.put("reason", "更新失败！！");
			return "page_400";
		}
		return "redirect:toOperator_monitorGroup_list?pageNum=1";
	}
    
	/*
	 * 通过页码查询数据
	 * search为可选参，用来查询监控组
	 * defaultGroupId为默认组，作用是不让用户删除该组
	 * 返回监控器组列表
	 */
    @RequestMapping(value = "/toOperator_monitorGroup_list")
    public String toOperator_monitorGroup_list(int pageNum,HttpServletRequest request,ModelMap modelMap){
    	if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
    	int operatorId =((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
    	int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
    	int defaultGroupId;
		//如果有搜索则调用下面函数
		if(request.getParameter("search")!=null){
			defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
			String groupName=request.getParameter("search");
			List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroups(userId, groupName);
			modelMap.addAttribute("defaultGroupId", defaultGroupId);
			modelMap.addAttribute("search", groupName);
			modelMap.addAttribute("list", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getDataList());
			modelMap.addAttribute("pageNum",pageNum);
			modelMap.addAttribute("totalPage", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getTotalPage());
			return "opviews/operator_monitorGroup_list";
		}
		defaultGroupId = monitorgroupService.getMonitorGroupsByUserId(userId).get(0).getGroupId();
		//如果没搜索，直接返回数据
		List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroupsByUserId(userId);
		modelMap.addAttribute("defaultGroupId", defaultGroupId);
		modelMap.addAttribute("list", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getDataList());
		modelMap.addAttribute("pageNum",pageNum);
		modelMap.addAttribute("totalPage", monitorgroupService.getMonitorGroups(monitorGroups,pageNum).getTotalPage());
    	return "opviews/operator_monitorGroup_list";
    }
  	
    /*
	 * 前往添加监控器组页面
	 */
  	@RequestMapping(value = "/toOperator_monitorgroup_add")
	public String toOperator_monitorgroup_add(HttpServletRequest request, ModelMap modelMap) {
  		if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
		return "opviews/operator_monitorgroup_add";
	}
    
  	/*
	 * 添加监控器组
	 * 返回监控组列表页面
	 */
  	@RequestMapping(value = "/operator_monitorgroup_add")
	public String operator_monitorgroup_add(MonitorGroup monitorGroup, HttpServletRequest request, ModelMap modelMap) {
  		if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
		int operatorId = ((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
		int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
		monitorGroup.setUser(userService.getUserById(userId));
		if(monitorgroupService.add(monitorGroup)!=1){
			modelMap.put("reason", "添加失败！！");
			return "page_400";
		}
		return "redirect:toOperator_monitorGroup_list?pageNum=1&operatorId="+operatorId;
	}
    
  	
  	/*
     * 前往监控器列表，与批量操作的监控器列表不一样
     * search为可选参
     * 返回监控器列表
     */
    @RequestMapping(value = "/toOperatorSeeMonitor")
    public String toOperatorSeeMonitor(int groupId,HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
        	int operatorId = ((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
    		int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            if(request.getParameter("search")!=null){
        		String monitorName=request.getParameter("search");
        		List<Monitoring> monitors = monitoringService.getMonitors(userId,groupId,monitorName);
        		Pager<Monitoring> pager = new Pager<Monitoring>(pageNum, 8, monitors);
        		modelMap.addAttribute("search", monitorName);
        		modelMap.addAttribute("pageNum", pageNum);
            	modelMap.addAttribute("totalPage", pager.getTotalPage());
            	modelMap.addAttribute("groupId", groupId);
            	modelMap.addAttribute("monitorList", pager.getDataList());
            	return "opviews/operator_monitorlist";
        	}
        	List<Monitoring> monitors = monitoringService.getMonitors(userId, groupId);
        	Pager<Monitoring> pager = new Pager<Monitoring>(pageNum, 8, monitors);
        	modelMap.addAttribute("monitorList", pager.getDataList());
        	modelMap.addAttribute("pageNum", pageNum);
        	modelMap.addAttribute("totalPage", pager.getTotalPage());
        	modelMap.addAttribute("groupId", groupId);
            return "opviews/operator_monitorlist";
        }
    }
  	
    /*
     * 前往批量操作的监控器列表页面
     * search为可选参，用于模糊查询监控器名称
     * 返回批量操作的监控器列表
     */
    @RequestMapping(value = "/toOperatorSeeMonitor1")
    public String toOperatorSeeMonitor1(int groupId,HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
        	int operatorId = ((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
    		int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            List<MonitorGroup> list = monitorgroupService.getMonitorGroupsByUserId(userId);
            if(request.getParameter("search")!=null){
        		String monitorName=request.getParameter("search");
        		List<Monitoring> monitors = monitoringService.getMonitors(userId,groupId,monitorName);
        		Pager<Monitoring> pager = new Pager<Monitoring>(pageNum, 8, monitors);
        		modelMap.addAttribute("monitorGroup", list);
        		modelMap.addAttribute("search", monitorName);
        		modelMap.addAttribute("pageNum", pageNum);
            	modelMap.addAttribute("totalPage", pager.getTotalPage());
            	modelMap.addAttribute("groupId", groupId);
            	modelMap.addAttribute("monitorList", pager.getDataList());
            	return "opviews/operator_monitorlist1";
        	}
        	List<Monitoring> monitors = monitoringService.getMonitors(userId, groupId);
        	Pager<Monitoring> pager = new Pager<Monitoring>(pageNum, 8, monitors);
        	modelMap.addAttribute("monitorGroup", list);
        	modelMap.addAttribute("monitorList", pager.getDataList());
        	modelMap.addAttribute("pageNum", pageNum);
        	modelMap.addAttribute("totalPage", pager.getTotalPage());
        	modelMap.addAttribute("groupId", groupId);
            return "opviews/operator_monitorlist1";
        }
    }
  	
  	
  	
    /**
     * 跳转到操作员添加监控器
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toOperatorAddMonitor")
    public String toOperatorAddMonitor(int groupId,HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            modelMap.addAttribute("groupId", groupId);
            return "opviews/op_add_monitor";
        }
    }

    /**
     * 添加监控器
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/operatorAddMonitor")
    public String operatorAddMonitor(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            Monitoring monitoring = new Monitoring();

            String monitorName = request.getParameter("monitorName");
            String monitorIp = request.getParameter("monitorIp");
            String monitorLocation = request.getParameter("monitorLocation");
            String monitorType = request.getParameter("monitorType");
            int operatorId = ((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
    		int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
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
            return "opviews/op_index";
        }
    }

    /*
     * 批量删除监控器
     * 返回监控器组列表
     */
    @RequestMapping(value = "/operatormonitor_deleteBoth")
    public String monitor_deleteBoth(int[] id,HttpServletRequest request, ModelMap modelMap) {
    	for(int i=0;i<id.length;i++){
    		monitoringService.delMonitoring(id[i]);
    	}
        return "redirect:toOperator_monitorGroup_list?pageNum=1";
    }
    
    /*
     * 批量移动监控器
     * 返回监控器组列表页面
     */
    @RequestMapping(value = "/operatormonitor_removeBoth")
    public String monitor_removeBoth(int[] id,int groupId,HttpServletRequest request, ModelMap modelMap) {
    	for(int i=0;i<id.length;i++){
    		monitoringService.alterGroupId(id[i], groupId);
    	}
        return "redirect:toOperator_monitorGroup_list?pageNum=1";
    }
    
    @RequestMapping(value = "/to_operator_monitor_info")
    public String to_operator_monitor_info(int id,HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }else {
            Monitoring monitoring = monitoringService.getMonitoringById(id);
            modelMap.addAttribute("monitoring", monitoring);
            return "opviews/operator_monitor_info";
        }
    }
  	
    /**
     * 跳转到更新页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toOperatorUpdateMonitor")
    public String toOperatorUpdateMonitor(HttpServletRequest request,ModelMap modelMap){
    	if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }else{
            int monitorId = Integer.parseInt(request.getParameter("monitorId"));
            int operatorId = ((Operator)request.getSession().getAttribute("OPERATOR")).getOperatorId();
    		int userId = userRelationService.getUserRelationBySonId(operatorId).getUser().getId();
            List<MonitorGroup> monitorGroups = monitorgroupService.getMonitorGroupsByUserId(userId);
            Monitoring monitor = monitoringService.getMonitoringById(monitorId);
            modelMap.addAttribute("monitor",monitor);
            modelMap.addAttribute("monitorGroups",monitorGroups);
            return "opviews/operator_monitor_update";
        }
    }

    /**
     * 更新摄像头信息
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/operatorUpdateMonitor")
    public String operatorUpdateMonitor(HttpServletRequest request,ModelMap modelMap){
    	if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
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
                
                return "redirect:to_operator_monitor_info?id="+monitorId;
            }else{
                modelMap.put("reason","更新出错请重新输入！！！");
                return "page_400";
            }
        }
    }
    
    /**
     * 查看历史视频
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toShowHistoryVideo")
    public String toShowHistoryVideo(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        }
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        int monitoringId = Integer.parseInt(request.getParameter("monitoringId"));
		Monitoring monitoring = monitoringService.getMonitoringById(monitoringId);
        Operator operator = (Operator) request.getSession().getAttribute("OPERATOR");
        User user = userRelationService.getUserRelationBySonId(operator.getOperatorId()).getUser();
        if(request.getParameter("startTime")!=null && request.getParameter("endTime")!=null
				&& !request.getParameter("startTime").equals("") && !request.getParameter("endTime").equals("")){
			String start = request.getParameter("startTime");
			String end = request.getParameter("endTime");
			Date startTime = TimeUtil.stringToDate(start);
			Date endTime = TimeUtil.stringToDate(end);
			Pager<Video> pager = videoService.getVideosByPageNum(videoService.getVideos(user,monitoring,startTime,endTime), pageNum);
			modelMap.addAttribute("startTime",start);
			modelMap.addAttribute("endTime",end);
			modelMap.addAttribute("videoList", pager.getDataList());
			modelMap.addAttribute("totalPage", pager.getTotalPage());
			modelMap.addAttribute("pageNum", pageNum);
			modelMap.addAttribute("monitoringId", monitoringId);
			return "opviews/operator_video_list";
		}
		Pager<Video> pager = videoService.getVideosByPageNum(videoService.getVideos(user,monitoring), pageNum);
		modelMap.addAttribute("videoList", pager.getDataList());
		modelMap.addAttribute("totalPage", pager.getTotalPage());
		modelMap.addAttribute("pageNum", pageNum);
		modelMap.addAttribute("monitoringId", monitoringId);
		return "opviews/operator_video_list";
    }
  	
    /**
     * 用户删除名下监控器
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/operatorDeleteMonitor")
    public String operatorDeleteMonitor(HttpServletRequest request, ModelMap modelMap) {
    	if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            int monitorId = Integer.parseInt(request.getParameter("monitorId"));
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            if(!monitoringService.delMonitoring(monitorId)){
                modelMap.put("reason","删除失败请重试！！！");
                return "page_400";
            }
            return "redirect:toOperatorSeeMonitor?pageNum=1&groupId="+groupId;
        }
    }
    
    
    
    
    
    
    
    
    
}























