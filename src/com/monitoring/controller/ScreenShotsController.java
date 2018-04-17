package com.monitoring.controller;

import com.monitoring.entity.Monitoring;
import com.monitoring.entity.ScreenShots;
import com.monitoring.service.ManagerService;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.ScreenShotsService;
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
public class ScreenShotsController {
    @Autowired
    ScreenShotsService screenShotsService;
    @Autowired
    MonitoringService monitoringService;
    @Autowired
    ManagerService managerService;
    @Autowired
    UserService userService;

    /**
     * 获取用户信息跳转到添加截图页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAddScreenShotsJsp")
    public String toAddScreenShotsJsp(HttpServletRequest request, ModelMap model) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<Monitoring> monitorings = monitoringService.getMonitoringsByUserId(userId);
            model.addAttribute("monitoringsList", monitorings);
            return "shots_add";
        }
    }

    /**
     * 添加截图
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addShots")
    public String addShots(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            String shotsName = request.getParameter("shotsName");
            String shotsURL = request.getParameter("shotsURL");
            Date shotsTime = new Date();
            int monitorId = Integer.parseInt(request.getParameter("monitorId"));
            ScreenShots screenShots = new ScreenShots();
            screenShots.setShotsName(shotsName);
            screenShots.setShotsURL(shotsURL);
            screenShots.setShotsTime(shotsTime);
            screenShots.setMonitor(monitoringService.getMonitoringById(monitorId));
            screenShotsService.addScreenShots(screenShots);
            return "index";
        }
    }

    /**
     * 用户查看所有的截图
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUserScreenShotsListJsp")
    public String toManagerScreenShotsListJsp(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            Pager<ScreenShots> pager = screenShotsService.getScreenShotsByUser(pageNum,userId);

            modelMap.addAttribute("shotsListMap", pager.getDataList());
            modelMap.addAttribute("totalPage", pager.getTotalPage());
            modelMap.addAttribute("pageNum", pager.getCurrentPage());
            return "user_shots_list";
        }
    }

    /**
     * 根据用户ID跳转到修改图片列表
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUpdateShotsJsp")
    public String toUpdateShotsJsp(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<Monitoring> monitorings = monitoringService.getMonitoringsByUserId(userId);
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));//页码
            ScreenShots screenShots = screenShotsService.getScreenShotsById(Integer.parseInt(request.getParameter("shotsId")));
            modelMap.addAttribute("screebShotsMap", monitorings);
            modelMap.addAttribute("screenShots",screenShots);
            modelMap.addAttribute("pageNum", pageNum);
            return "shots_update";
        }
    }

    /**
     * 修改截图信息
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateScreenShots")
    public String updateScreenShots(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            String shotsId = request.getParameter("shotsId");
            String shotsName = request.getParameter("shotsName");
            String shotsURL = request.getParameter("shotsURL");

            ScreenShots screenShots = new ScreenShots();
            screenShots.setShotsId(Integer.parseInt(shotsId));
            screenShots.setShotsName(shotsName);
            screenShots.setShotsURL(shotsURL);

            if (screenShotsService.updateScreenShots(screenShots)) {
                //修改成功
                return "redirect:/toScreenShotsListJsp?pageNum=" + pageNum;
            }
            modelMap.put("reason", "操作失败，请确认操作！");
            return "page_400";
        }

    }

    /**
     * 删除截图
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/deleteScreenShots")
    public String deleteScreenShots(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        } else {
            String shotsId = request.getParameter("shotsId");
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));

            screenShotsService.deleteScreenShotsById(Integer.parseInt(shotsId));
            List<ScreenShots> list = screenShotsService.getScreenShotsPageList(pageNum);
            System.out.println("user分页list=" + list.toString());
            modelMap.addAttribute("list", list);
            modelMap.addAttribute("pageNums", userService.getPageNum());
            modelMap.addAttribute("pageNum", pageNum);
            return "shots_list";
        }
    }
}
