package com.monitoring.controller;

import com.monitoring.entity.User;
import com.monitoring.service.ManagerService;
import com.monitoring.service.UserGradeService;
import com.monitoring.service.UserService;
import com.monitoring.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpServletRequest;

@SessionAttributes(value = "manager")
@Controller
public class ManagerController {
    @Autowired
    UserService userService;
    @Autowired
    UserGradeService userGradeService;
    @Autowired
    ManagerService managerService;

    /**
     * 注册审核列表
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userRegisterList")
    public String clientRegisterList(HttpServletRequest request, ModelMap modelMap) {
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Pager<User> pager = userService.getUserByUserGradeId(pageNum, 1);

        System.out.println("user分页list=" + pager.toString());
        modelMap.addAttribute("list", pager.getDataList());
        modelMap.addAttribute("totalPage", pager.getTotalPage());
        modelMap.addAttribute("pageNum", pageNum);
        return "registercheck_list";
    }

    /**
     * 审核注册用户，是否通过注册
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/changeUserRegistState")
    public String changeUserRegistState(HttpServletRequest request, ModelMap modelMap) {
        int id = Integer.parseInt(request.getParameter("id"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));//页码
        if(userService.isOrNotAgreeAdd(id)){
            Pager<User> pager = userService.getUserByUserGradeId(pageNum, 1);

            System.out.println("user分页list=" + pager.toString());
            modelMap.addAttribute("list", pager.getDataList());
            modelMap.addAttribute("totalPage", pager.getTotalPage());
            modelMap.addAttribute("pageNum", pageNum);
            return "registercheck_list";
        }else{
            modelMap.put("reason","操作失败，请重试！！！");
            return "page_400";
        }

    }


}
