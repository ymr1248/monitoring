/**
 *
 */
package com.monitoring.controller;

import com.monitoring.entity.*;
import com.monitoring.service.*;
import com.monitoring.util.MyTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Lee 2017年12月18日
 */
@Controller
public class PageController {
    @Autowired
    ManagerService managerService;
    @Autowired
    MonitorgroupService monitorgroupService;
    @Autowired
    MonitoringService monitoringService;
    @Autowired
    OperatorService operatorService;
    @Autowired
    UserService userService;


    @RequestMapping(value = "/page_403")
    public String page_403(HttpServletRequest request, ModelMap modelMap) {
        return "page_403";
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            return "index";
        }
    }


    @RequestMapping(value = "/userIndex")
    public String userIndex(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
			/*User user =(User) request.getSession().getAttribute("USER");	
			List<MonitorGroup> monitorGroupList = monitorgroupService.getMonitorGroupsByUserId(user.getId());	
			List<Monitoring> monitoringList = monitoringService.getMonitoringsByUserId(user.getId());
			modelMap.addAttribute("monitorGroupList", monitorGroupList);
			modelMap.addAttribute("monitoringList", monitoringList);*/
            return "userviews/user_index";
        }
    }

/*	@RequestMapping(value = "/userIndex")
	public String userIndex(HttpServletRequest request, ModelMap modelMap){
		if (MyTestUtils.userIsLogined(request)!=null) {
			return "page_403";
		}else {
			User user =(User) request.getSession().getAttribute("USER");	
			List<MonitorGroup> monitorGroupList = monitorgroupService.getMonitorGroupsByUserId(user.getId());	
			modelMap.addAttribute("monitorGroupList", monitorGroupList);
			return "userviews/user_index2";
		}
	}*/

    @RequestMapping(value = "/opIndex")
    public String opIndex(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.operatorIsLogined(request) != null) {
            return "page_403";
        } else {
            return "opviews/op_index";
        }
    }

    @RequestMapping(value = "/")
    public String index1(HttpServletRequest request, ModelMap modelMap) {
        return "login";
    }

    @RequestMapping(value = "/page_400")
    public String page_400(HttpServletRequest request, ModelMap modelMap) {
        return "page_400";
    }

    @RequestMapping(value = "/page_404")
    public String page_404(HttpServletRequest request, ModelMap modelMap) {
        return "page_404";
    }

    @RequestMapping(value = "/page_500")
    public String page_500(HttpServletRequest request, ModelMap modelMap) {
        return "page_500";
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, ModelMap modelMap) {
        return "login";
    }

    @RequestMapping(value = "/userlogin")
    public String userLogin(HttpServletRequest request, ModelMap modelMap) {
        return "userviews/user_login";
    }

    @RequestMapping(value = "/oplogin")
    public String opLogin(HttpServletRequest request, ModelMap modelMap) {
        return "opviews/op_login";
    }

    @RequestMapping(value = "/checkLogin")
    public String checkLogin(HttpServletRequest request, ModelMap modelMap) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        int checkNum = Integer.parseInt(request.getParameter("checkNum"));
        if (checkNum == 1) {
            int isLogin = userService.loginUser(account, password);
            if (isLogin == 1) {
                User user = userService.getUserByAccount(account);
                request.getSession().setAttribute("USER", user);
                return "redirect:userIndex";
            } else if (isLogin == 0) {
                //密码错误
                modelMap.put("reason", "密码错误，请检查您的密码是否正确？");
                return "page_400";
            } else if (isLogin == 2) {
                //未通过审核
                modelMap.put("reason", "未通过审核，请等待审核通过！！！");
                return "page_400";
            } else {
                //账号错误
                modelMap.put("reason", "账号错误，请检查您的账号是否正确？");
                return "page_400";
            }
        } else if (checkNum == 2) {
            int isLogin = operatorService.loginOperator(account, password);
            if (isLogin == 1) {
                Date date = new Date();
                Operator operator = operatorService.getOperatorByAccount(account);
                operatorService.resetApplyPermission(operator.getOperatorId(), date);
                request.getSession().setAttribute("OPERATOR", operator);
                return "opviews/op_index";
            } else if (isLogin == 0) {
                //密码错误
                modelMap.put("reason", "密码错误，请检查您的密码是否正确？");
                return "page_400";
            } else {
                //账号错误
                modelMap.put("reason", "账号错误，请检查您的账号是否正确？");
                return "page_400";
            }
        } else {
            int isLogin = managerService.managerLogin(account, password);
            if (isLogin == 1) {
                Manager manager = managerService.getManagerByAccount(account);
                request.getSession().setAttribute("MANAGER", manager);
                return "index";
            } else if (isLogin == 0) {
                //密码错误
                modelMap.put("reason", "密码错误，请检查您的密码是否正确？");
                return "page_400";
            } else {
                //账号错误
                modelMap.put("reason", "账号错误，请检查您的账号是否正确？");
                return "page_400";
            }
        }
    }
}
