package com.monitoring.appcontroller;

import com.google.gson.Gson;
import com.monitoring.entity.Operator;
import com.monitoring.entity.Permission;
import com.monitoring.entity.User;
import com.monitoring.entity.UserRelation;
import com.monitoring.service.*;
import com.monitoring.util.MyObject;
import com.monitoring.util.MyTestUtils;
import com.monitoring.util.Pager;
import com.monitoring.util.ResponseUtils;
import org.hibernate.annotations.GenericGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@SessionAttributes(value = "operator")
@Controller
public class AppOperatorController {
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
     * 操作员登录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/appOperatorLogin")
    public void appOperatorLogin(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        int flag = 0;
        int isLogin = operatorService.loginOperator(account, password);
        Gson gson = new Gson();
        if (isLogin == 1) {
            Date date = new Date();
            Operator operator = operatorService.getOperatorByAccount(account);
            operatorService.resetApplyPermission(operator.getOperatorId(), date);
            request.getSession().setAttribute("OPERATOR", operator);
            flag = 1;
        } else if (isLogin == 0) {
            //密码错误
            flag = 0;
        } else {
            //账号错误
            flag = -1;
        }
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 高级权限管理低级权限
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appHigherOperatorManageLower")
    public void appHigherOperatorManageLower(HttpServletRequest request, HttpServletResponse response) {
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Pager<Operator> operatorPager = operatorService.getOperatorByHigherPermissionId(pageNum, operatorId);
        Gson gson = new Gson();
        int flag = 0;
        String date = gson.toJson(operatorPager);
        if (operatorPager.getTotalPage() != 0) {
            flag = 1;
        }
        MyObject myObject = new MyObject(flag, date);
        String json = gson.toJson(myObject);
        ResponseUtils.renderJson(response, json);
    }

    /**
     * 添加低权限操作员
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appAddLowerOperator")
    public void appAddLowerOperator(HttpServletRequest request, HttpServletResponse response) {

        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        User user = userRelationService.getUserRelationBySonId(operatorId).getUser();
        int flag = 0;
        String operatorName = request.getParameter("operatorName");
        String password = request.getParameter("password");
        String operatorAccount = request.getParameter("operatorAccount");
        String operatorAddress = request.getParameter("operatorAddress");
        String operatorPhone = request.getParameter("operatorPhone");
        Permission permission = Permission.valueOf(request.getParameter("permission"));
        Operator operatorCheck = operatorService.getOperatorByAccount(operatorAccount);
        if (operatorCheck != null) {
            flag = -1;
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


            UserRelation userRelation = new UserRelation();
            userRelation.setUser(user);
            userRelation.setOperator(operatorService.getOperatorByAccount(operatorAccount));
            userRelationService.addUserRelation(userRelation);
            flag = 1;
        } else {
            flag = 0;
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 对低级权限的操作员做信息更新
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUpdateLowerOperator")
    public void appUpdateLowerOperator(HttpServletRequest request, HttpServletResponse response) {
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        String operatorName = request.getParameter("operatorName");
        String password = request.getParameter("password");
        String operatorAddress = request.getParameter("operatorAddress");
        String operatorPhone = request.getParameter("operatorPhone");
        Permission permission = Permission.valueOf(request.getParameter("permission"));
        Gson gson = new Gson();
        int flag = 0;
        Operator operator = new Operator();
        operator.setOperatorName(operatorName);
        operator.setOperatorAddress(operatorAddress);
        operator.setOperatorPhone(operatorPhone);
        operator.setPassword(password);
        operator.setPermission(permission);
        operator.setOperatorId(operatorId);
        if (operatorService.update(operator)) {
            flag = 1;
        }
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);

    }

    /**
     * 删除低级操作员
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appDeleteLowerOperator")
    public void appDeleteLowerOperator(HttpServletRequest request, HttpServletResponse response) {

        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        int flag = 0;
        Gson gson = new Gson();
        if (userRelationService.deleteUserRelationBySonId(operatorId)) {
            if (operatorService.deleteOperatorById(operatorId)) {
                flag = 1;
            } else {
                flag = 0;
            }
        } else {
            flag = -1;
        }
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * app管理下级用户申请
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appManageApplyPermission")
    public void appManageApplyPermission(HttpServletRequest request, HttpServletResponse response) {

        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        Operator operator = operatorService.getOperatorById(operatorId);
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Pager<Operator> operatorPager = operatorService.getOperatorByApplyState(pageNum, operatorId, operator.getPermission().ordinal());
        int flag = 0;
        if (operatorPager.getTotalPage() != 0) {
            flag = 1;
        }
        Gson gson = new Gson();
        String data = gson.toJson(operatorPager);
        MyObject myObject = new MyObject(flag, data);
        String json = gson.toJson(myObject);

        ResponseUtils.renderJson(response, json);

    }

    /**
     * App申请权限
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appApplyForPermission")
    public void applyForPermission(HttpServletRequest request, HttpServletResponse response) {
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        Permission permission = Permission.valueOf(request.getParameter("permission"));
        int permissionOrdinal = permission.ordinal();
        int flag = 0;
        Gson gson = new Gson();
        if (operatorService.updateApplyState(operatorId, permissionOrdinal)) {
            flag = 1;
        } else {
            flag = 0;
        }
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 同意申请权限
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appAgreeApplyPermission")
    public void appAgreeApplyPermission(HttpServletRequest request, HttpServletResponse response) {
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        int flag = 0;
        Gson gson = new Gson();
        if (operatorService.agreePermissionTime(operatorId)) {
            flag = 1;
        } else {
            flag = 0;
        }
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 不同意通过申请
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appDisagreeApplyPermission")
    public void appDisagreeApplyPermission(HttpServletRequest request, HttpServletResponse response) {
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        int flag = 0;
        if (operatorService.updateApplyState(operatorId, -1)) {
            flag = 1;
        } else {
           flag = 0;
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

}
