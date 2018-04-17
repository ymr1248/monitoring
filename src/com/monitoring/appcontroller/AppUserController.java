package com.monitoring.appcontroller;

import com.google.gson.Gson;
import com.monitoring.entity.*;
import com.monitoring.service.*;
import com.monitoring.util.*;
import com.mysql.jdbc.log.Log;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.UUID;

@SessionAttributes(value = "user")
@Controller
public class AppUserController {
    @Autowired
    UserService userService;
    @Autowired
    UserGradeService userGradeService;
    @Autowired
    ManagerService managerService;
    @Autowired
    UserRelationService userRelationService;
    @Autowired
    OperatorService operatorService;

    
    /**
     * 客户端登录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/appUserLogin")
    public void appUserLogin(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String appId = request.getParameter("appId");

        
        Gson gson = new Gson();
        int isLogin = userService.loginUser(account, password);
        User user = userService.getUserByAccount(account);
        userService.updateUserAppId(user.getId(),appId);
        String data = gson.toJson(user);
        MyObject myObject = new MyObject(isLogin,data);
        String resource = gson.toJson(myObject);
        ResponseUtils.renderJson(response, resource);

    }

    /**
     * 添加操作员接口
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/appAddOperator")
    public void appAddOperator(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));

        int result;
        String operatorName = request.getParameter("operatorName");
        String password = request.getParameter("password");
        String operatorAccount = request.getParameter("operatorAccount");
        String operatorAddress = request.getParameter("operatorAddress");
        String operatorPhone = request.getParameter("operatorPhone");
        Permission permission = Permission.valueOf(request.getParameter("permission"));
        Operator operatorCheck = operatorService.getOperatorByAccount(operatorAccount);
        if (operatorCheck != null) {
            result = -1;
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
            userRelation.setUser(userService.getUserById(userId));
            userRelation.setOperator(operatorService.getOperatorByAccount(operatorAccount));

            userRelationService.addUserRelation(userRelation);
            result = 1;
        } else {
            result = -1;
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(result, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 用户查看名下操作员列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUserOperatorList")
    public void appUserOperatorList(HttpServletRequest request, HttpServletResponse response) {
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        Gson gson = new Gson();
        int flag = 0;
        Pager<Operator> operators = operatorService.getOperatorWithPage(pageNum, userId);
        System.out.println("user分页list=" + operators.toString());
        String date = gson.toJson(operators);
        if (operators.getTotalPage() != 0) {
            flag = 1;
        }
        MyObject myObject = new MyObject(flag, date);
        String json = gson.toJson(myObject);
        ResponseUtils.renderJson(response, json);
    }

    /**
     * app用户更新操作员信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appOperatorUpdate")
    public void appOperatorUpdate(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        int resource = 0;
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
        if (operatorService.update(operator)) {
            resource = 1;
        }
        MyObject myObject = new MyObject(resource, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * app用户删除操作员
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appOperatorDelete")
    public void appOperatorDelete(HttpServletRequest request, HttpServletResponse response) {
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        int flag = 0;
        if (userRelationService.deleteUserRelationBySonId(operatorId)) {
            if (operatorService.deleteOperatorById(operatorId)) {
                flag = 1;
            }
            flag = 0;
        } else {
            flag = -1;
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * App客户注册接口
     * 可以在这里添加手机号码注册验证码确认
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appRegisterUser")
    public void appRegisterUser(HttpServletRequest request, HttpServletResponse response) {
        int flag = 0;
        String userAccount = request.getParameter("account");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String userPhone = request.getParameter("userPhone");
        String userAddress = request.getParameter("userAddress");
        String avatar = null;
        String userEmail;
        if (request.getParameter("userEmail") != null) {
            userEmail = request.getParameter("userEmail");
        } else {
            userEmail = null;
        }
        int userGradeId = 1;
        int userIsAdded = userService.isOrNotAdded(avatar, userName, userAccount, password, userAddress, userPhone, userEmail, userGradeId);
        if (userIsAdded == 1) {
            flag = 1;//注册成功
        } else if (userIsAdded == 0) {
            flag = 0;//手机号已被使用
        } else if (userIsAdded == -1) {
            flag = -1;//账号已被注册
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);

    }

    /**
     * App用户查看自己的信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUserToCheckInfo")
    public void appUserToCheckInfo(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userService.getUserById(userId);
        int flag = 0;
        if (user != null) {
            flag = 1;
        }
        Gson gson = new Gson();
        String data = gson.toJson(user);
        MyObject myObject = new MyObject(flag, data);
        String json = gson.toJson(myObject);
        ResponseUtils.renderJson(response, json);
    }

    /**
     * App用户修改自己的信息
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUserSelfUpdate")
    public void appUserSelfUpdate(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        String fileName = "";
        if (file != null) {
            String picture = file.getOriginalFilename();
            String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/img/avatar/";
            //					+ UUID.randomUUID() + picture.substring(picture
            //	                        .lastIndexOf("."));
            fileName = UUID.randomUUID() + picture.substring(picture.lastIndexOf("."));
            File targetFile = new File(filePath, fileName); // 新建文件
            if (!targetFile.exists()) { // 判断文件的路径是否存在
                targetFile.mkdirs(); // 如果文件不存在 在目录中创建文件夹 这里要注意mkdir()和mkdirs()的区别
            }
            // 保存
            try {
                file.transferTo(targetFile); // 传送 失败就抛异常
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 执行更新图片在服务器的地址
        }
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userAddress = request.getParameter("userAddress");
        String userName = request.getParameter("userName");
        String userEMail = request.getParameter("userEMail");
        int userGradeId = Integer.parseInt(request.getParameter("userGradeId"));

        User user = new User();
        user.setUserAddress(userAddress);
        user.setAvatar(fileName);
        user.setUserName(userName);
        user.setUserEMail(userEMail);
        user.setUserGrade(userGradeService.getUserGradeById(userGradeId));
        user.setId(userId);

        Gson gson = new Gson();
        int flag = 0;
        if (userService.update(user)) {
            flag = 1;
        } else {
            flag = 0;
        }
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 更新个人密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUpdateMyPsw")
    public void appUpdateMyPsw(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        int flag = 0;
        if (userService.checkUserPsw(userId, oldPassword)) {
            //旧密码验证成功,更新密码
            if (userService.updateUserPsw(userId, newPassword)) {
                //密码修改成功
                flag = 1;
            } else {

                flag = -1;
            }
        } else {
            flag = 0;
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(flag, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 通过短信验证更新个人密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUpdateMyPswByMessage")
    public void appUpdateMyPswByMessage(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String messageCode = request.getParameter("messageCode");
        String newPassword = request.getParameter("newPassword");
        
        /*
         * 这里放判断短信验证码是否一致
         */
        Gson gson = new Gson();
        MyObject myObject = new MyObject(1, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }  
    
    /**
     * 通过邮箱验证更新个人密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUpdateMyPswByEmail")
    public void appUpdateMyPswByEmail(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String messageCode = request.getParameter("messageCode");
        String newPassword = request.getParameter("newPassword");
        
        /*
         * 这里放判断邮箱验证码是否一致
         */
        Gson gson = new Gson();
        MyObject myObject = new MyObject(1, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    } 
    
    /**
     * 通过邮箱验证更新个人密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appGetMessageCode")
    public void appGetMessageCode(HttpServletRequest request, HttpServletResponse response) {
        /*
         * 这里放短信验证码,已短信形式发送给手机端
         */
        Gson gson = new Gson();
        MyObject myObject = new MyObject(1, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    } 
    
    /**
     * 通过邮箱验证更新个人密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appGetEmailCode")
    public void appGetEmailCode(HttpServletRequest request, HttpServletResponse response) {
        /*
         * 这里放邮箱验证码,已邮箱形式发送给用户邮箱
         */
        Gson gson = new Gson();
        MyObject myObject = new MyObject(1, "");
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
    }

    /**
     * 消息推送成功返回一失败返回0
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sendMessageToClient")
    public void sendMessageToClient(HttpServletRequest request,HttpServletResponse response)throws Exception{
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userService.getUserById(userId);
        String userAccount = user.getUserAccount();
        String appId = user.getAppId();
        Constants.useOfficial();
        Sender sender = new Sender("sv1ey5gp6NeUEyC2GlovUw==");
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName("com.monitoring.appcontroller")
                .notifyType(1)     // 使用默认提示音提示
                .build();
        //Result result = sender.sendToUserAccount(message, userAccount, 0); //Result对于sendToAlias()，broadcast()和send()调用方式完全一样
        Result result = sender.send(message,appId,0);
        int flag = 0;
        if(result.getMessageId() != null){
            flag = 1;
        }
        Gson gson = new Gson();
        MyObject myObject = new MyObject(flag, "");
        String json = gson.toJson(myObject);
        ResponseUtils.renderJson(response, json);
    }


    
}
