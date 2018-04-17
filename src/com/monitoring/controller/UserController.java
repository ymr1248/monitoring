package com.monitoring.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.monitoring.entity.Operator;
import com.monitoring.entity.Permission;
import com.monitoring.entity.User;
import com.monitoring.entity.UserGrade;
import com.monitoring.entity.UserRelation;
import com.monitoring.service.ManagerService;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.OperatorService;
import com.monitoring.service.ScreenShotsService;
import com.monitoring.service.SpaceService;
import com.monitoring.service.UserGradeService;
import com.monitoring.service.UserRelationService;
import com.monitoring.service.UserService;
import com.monitoring.service.VideoService;
import com.monitoring.util.CyptoUtils;
import com.monitoring.util.MyTestUtils;
import com.monitoring.util.Pager;

@SessionAttributes(value = "user")
@Controller
public class UserController {
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
    @Autowired
    VideoService videoService;
    @Autowired
    ScreenShotsService screenShotsService;
    @Autowired
    MonitoringService monitoringService;
    @Autowired
    MonitorgroupService monitorgroupService;
    @Autowired
    SpaceService spaceService;
    
    
    /**
     * 用户列表
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userList")
    public String userList(HttpServletRequest request, ModelMap modelMap) {

        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            List<User> list = userService.getUsers(pageNum);

            modelMap.addAttribute("list", list);
            modelMap.addAttribute("totalPage", userService.getPageNum());
            modelMap.addAttribute("pageNum", pageNum);
            return "user_list";
        }


    }

    @RequestMapping(value = "/toAddUser")
    public String toAddUser(HttpServletRequest request, ModelMap modelMap) {

        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            //获取用户等级
            List<UserGrade> userGradeList = userGradeService.getUserGrades();
            modelMap.addAttribute("gradeList", userGradeList);
            return "user_add";
        }

    }

    @RequestMapping(value = "/addUser")
    public String addUser(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            String fileName = "";
            if (file != null) {
                fileName = file.getOriginalFilename();// 获取上传的文件名字
                String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/img/usericon/"
                        + file.getOriginalFilename();
                System.out.println("filePath==" + filePath + " " + fileName);
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
            String avatar = fileName;
            String userName = request.getParameter("inputName");
            String userAccount = request.getParameter("inputAccount");
            String password = request.getParameter("inputPassword");
            String userAddress = request.getParameter("inputAddress");
            int userGradeId = Integer.parseInt(request.getParameter("inputUserGradeId"));
            String userPhone = request.getParameter("inputPhone");
            String userEmail = request.getParameter("inputEmail");

            int userIsAdded = userService.isOrNotAdded(avatar, userName, userAccount, password, userAddress, userPhone, userEmail, userGradeId);
            if (userIsAdded == 1) {
                //获取用户类型
                List<UserGrade> userGradeList = userGradeService.getUserGrades();

                modelMap.addAttribute("gradeList", userGradeList);
                return "index";
            } else if (userIsAdded == -1) {
                modelMap.put("reason", "账号已存在请重新输入！");
                return "page_400";
            } else if (userIsAdded == 0) {
                modelMap.put("reason", "手机号已被使用，请重新输入！");
                return "page_400";
            } else {
                modelMap.put("reason", "未知错误，请重新输入！");
                return "page_400";
            }
        }

    }

    /**
     * 删除用户
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userDel")
    public String userDel(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            videoService.delVideoByUserId(id);
            screenShotsService.deleteScreenShotsByUserId(id);
            monitoringService.delMonitoringByUserId(id);
            monitorgroupService.deleteByUserId(id);
            spaceService.deleteSpaceByUserId(id);
            userRelationService.deleteUserRelationByFatherId(id);
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            userService.deleteUserById(id);
            List<User> list = userService.getUsers(pageNum);
            List<UserGrade> userGradeList = userGradeService.getUserGrades();
            System.out.println("user分页list=" + list.toString());
            modelMap.addAttribute("list", list);
            modelMap.addAttribute("gradeList", userGradeList);
            modelMap.addAttribute("pageNums", userService.getPageNum());
            modelMap.addAttribute("pageNum", pageNum);
            return "user_list";
        }
    }

    /**
     * 转到更新用户信息界面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUpdateUser")
    public String toUpdateUser(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            String id = request.getParameter("id");
            List<UserGrade> userGradeList = userGradeService.getUserGrades();
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));

            HashMap<String, String> map = new HashMap<String, String>();
            if (id != null) {
                User user = userService.getUserById(Integer.parseInt(id));
                map.put("id", String.valueOf(user.getId()));
                map.put("userName", user.getUserName());
                map.put("userAddress", user.getUserAddress());
                map.put("userEMail", user.getUserEMail());
                map.put("avatar", user.getAvatar());
                map.put("userGradeId", String.valueOf(user.getUserGrade().getId()));
                map.put("userGrade", String.valueOf(user.getUserGrade().getUserGrade()));
                map.put("userGradeName", user.getUserGrade().getUserGradeName());

            } else {
                map.put("id", "0");
                map.put("userAddress", "地址");
                map.put("userName", "姓名");
                map.put("userEMail", "邮箱");
                map.put("avatar", "头像");
                map.put("userGradeId", "等级ID");
                map.put("userGrade", "");
                map.put("userGradeName", "等级名称");
            }

            modelMap.addAttribute("map1", map);
            modelMap.addAttribute("gradeList", userGradeList);
            modelMap.addAttribute("pageNum", pageNum);
            return "user_update";
        }
    }


    /**
     * 更新用户数据
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userUpdate")
    public String updateUser(@RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            User user = new User();
            String fileName = "";
            if (!file.getOriginalFilename().equals("")) {
                String picture = file.getOriginalFilename();
                String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/img/avatar/";
                //					+ UUID.randomUUID() + picture.substring(picture
                //	                        .lastIndexOf("."));
                fileName = UUID.randomUUID() + picture.substring(picture.lastIndexOf("."));
                System.out.println("**********" + filePath);
                System.out.println("**********" + fileName);
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
            System.out.println("头像图片名字==" + fileName);
            String id = request.getParameter("id");
            String userName = request.getParameter("userName");
            String userEMail = request.getParameter("userEMail");
            String userAddress = request.getParameter("userAddress");
            String userGradeId = request.getParameter("userGradeId");
            // String userPhone = request.getParameter("userPhone");

            user.setUserAddress(userAddress);
            user.setUserName(userName);
            user.setAvatar(fileName);
            user.setUserEMail(userEMail);
            // user.setUserPhone(userPhone);
            user.setId(Integer.parseInt(id));
            user.setUserGrade(userGradeService.getUserGradeById(Integer.parseInt(userGradeId)));
            System.out.println(user.toString());
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            if (userService.update(user)) {
                //成功
                List<User> list = userService.getUsers(pageNum);

                System.out.println("user分页list=" + list.toString());
                modelMap.addAttribute("list", list);
                modelMap.addAttribute("pageNums", userService.getPageNum());
                modelMap.addAttribute("pageNum", pageNum);
                return "user_list";
            }
            return "update_error";
        }
    }

    /**
     * 重置用户密码
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateUserPSW")
    public String updateUserPSW(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            String id = request.getParameter("id");
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));

            if (userService.updateUserPsw(Integer.parseInt(id), "123456")) {
                //密码修改成功
                List<User> list = userService.getUsers(pageNum);


                System.out.println("user分页list=" + list.toString());
                modelMap.addAttribute("list", list);
                modelMap.addAttribute("pageNums", userService.getPageNum());
                modelMap.addAttribute("pageNum", pageNum);
                return "user_list";

            }
            return "update_error";
        }
    }

    /**
     * 跳转到添加操作员的页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAddOperator")
    public String toAddOperator(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
            User user = userService.getUserById(userId);
            modelMap.addAttribute("map", user);
            return "userviews/operator_add";
        }
    }

    /**
     * 添加用户
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addOperator")
    public String addOperator(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
            User userMap = userService.getUserById(userId);
            modelMap.addAttribute("map", userMap);

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
                userRelation.setUser(userService.getUserById(userId));
                userRelation.setOperator(operatorService.getOperatorByAccount(operatorAccount));

                userRelationService.addUserRelation(userRelation);

                int pageNum = 1;
                return "redirect:/userOperatorList?pageNum=" + pageNum + "&userId=" + CyptoUtils.encode(String.valueOf(userId));
            } else {
                modelMap.put("reason", "电话号码已注册，请重新输入账号！");
                return "page_400";
            }
        }
    }


    /**
     * 用户查看名下操作员列表
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userOperatorList")
    public String userOperatorList(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
            User userMap = userService.getUserById(userId);

            modelMap.addAttribute("map", userMap);
            Pager<Operator> operators = operatorService.getOperatorWithPage(pageNum, userId);
            System.out.println("user分页list=" + operators.toString());
            modelMap.addAttribute("list", operators.getDataList());
            modelMap.addAttribute("totalPage", operators.getTotalPage());
            modelMap.addAttribute("pageNum", pageNum);
            return "userviews/operator_list";
        }
    }

    /**
     * 转到更新操作员信息页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toOperatorUpdate")
    public String toOperatorUpdate(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            int operatorId = Integer.parseInt(request.getParameter("operatorId"));
            Operator operator = operatorService.getOperatorById(operatorId);

            modelMap.addAttribute("operator", operator);
            return "userviews/operator_update";
        }
    }

    /**
     * 用户更新操作员信息
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/operatorUpdate")
    public String operatorUpdate(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
            User userMap = userService.getUserById(userId);
            modelMap.addAttribute("map", userMap);

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

            int pageNum = 1;
            return "redirect:/userOperatorList?pageNum=" + pageNum + "&userId=" + CyptoUtils.encode(String.valueOf(userId));

        }
    }

    /**
     * 用户删除操作员
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/operatorDelete")
    public String operatorDelete(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            int operatorId = Integer.parseInt(request.getParameter("operatorId"));
            int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            if (userRelationService.deleteUserRelationBySonId(operatorId)) {
                if (operatorService.deleteOperatorById(operatorId)) {
                    if (pageNum == 0) {
                        pageNum = 1;
                        return "redirect:/userOperatorList?pageNum=" + 1 + "&userId=" + CyptoUtils.encode(String.valueOf(userId));
                    }
                    return "redirect:/userOperatorList?pageNum=" + pageNum + "&userId=" + CyptoUtils.encode(String.valueOf(userId));
                }
                return "page_400";
            } else {
                return "page_400";
            }
        }
    }

    /**
     * 跳转到注册页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toRegister")
    public String toRegister(HttpServletRequest request, ModelMap modelMap) {
        return "userviews/regist_user";
    }

    /**
     * 客户注册接口
     * 可以在这里添加手机号码注册验证码确认
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/registerUser")
    public String registerUser(HttpServletRequest request, ModelMap modelMap) {
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
        if (userService.getUserByAccount(userAccount) != null) {
            modelMap.put("reason", "已存在该账号，请重新输入账号！");
            return "page_400";
        } else if (userService.checkPhone(userPhone)) {
            int userIsAdded = userService.isOrNotAdded(avatar, userName, userAccount, password, userAddress, userPhone, userEmail, userGradeId);
            if (userIsAdded == 1) {
                //获取用户类型
                List<UserGrade> userGradeList = userGradeService.getUserGrades();

                modelMap.addAttribute("gradeList", userGradeList);
                return "login";
            } else if (userIsAdded == -1) {
                modelMap.put("reason", "账号已存在请重新输入！");
                return "page_400";
            } else if (userIsAdded == 0) {
                modelMap.put("reason", "手机号已被使用，请重新输入！");
                return "page_400";
            } else {
                modelMap.put("reason", "未知错误，请重新输入！");
                return "page_400";
            }
        } else {
            modelMap.put("reason", "手机号已被注册请重新输入！！！");
            return "page_400";
        }

    }

    /**
     * 用户查看自己的信息
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userToCheckInfo")
    public String userToCheckInfo(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            User user = (User) request.getSession().getAttribute("USER");
            modelMap.addAttribute("user", user);
            return "userviews/user_check_info";
        }
    }

    /**
     * 转到用户修改自己信息
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUserSelfUpdate")
    public String toUserSelfUpdate(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            User user = (User) request.getSession().getAttribute("USER");
            modelMap.addAttribute("user", user);
            return "userviews/user_self_update";
        }
    }

    /**
     * 用户修改自己的信息
     *
     * @param file
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userSelfUpdate")
    public String userSelfUpdate(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            String fileName = "";
            String filePath = "";
            if (file != null) {
                String picture = file.getOriginalFilename();
                filePath = request.getSession().getServletContext().getRealPath("/") + "resources/img/avatar/";
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
            User user = (User) request.getSession().getAttribute("USER");
            int userId = user.getId();
            String userAddress = request.getParameter("userAddress");
            String userName = request.getParameter("userName");
            String userEMail = request.getParameter("userEMail");
            int userGradeId = Integer.parseInt(request.getParameter("userGradeId"));

            User user1 = new User();
            user1.setUserAddress(userAddress);
            user1.setAvatar(fileName);
            user1.setUserName(userName);
            user1.setUserEMail(userEMail);
            user1.setUserGrade(userGradeService.getUserGradeById(userGradeId));
            user1.setId(userId);

            if (userService.update(user1)) {
                return "userviews/user_index";
            } else {
                modelMap.put("reason", "更新数据出错请重试！！！");
                return "page_400";
            }
        }
    }

    /**
     * 跳转到修改个人用户密码
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUpdateMyPsw")
    public String toUpdateMyPsw(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.userIsLogined(request) != null) {
            return "page_403";
        } else {
            User user = (User) request.getSession().getAttribute("USER");
            modelMap.addAttribute("user", user);
            return "userviews/update_mypsw";
        }
    }

    /**
     * 更新个人密码
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateMyPsw")
    public String updateMyPsw(HttpServletRequest request, ModelMap modelMap) {
        User user = (User)request.getSession().getAttribute("USER");
        int userId = user.getId();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        if (userService.checkUserPsw(userId, oldPassword)) {
            //旧密码验证成功,更新密码
            if (userService.updateUserPsw(userId, newPassword)) {
                //密码修改成功
                return "userviews/user_index";
            } else {
                modelMap.put("reason", "密码验证成功但修改失败，请重试！");
                return "page_400";
            }
        } else {
            modelMap.put("reason", "密码验证失败，请确认您的密码！");
            return "page_400";
        }
    }




}
