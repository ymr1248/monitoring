package com.monitoring.appcontroller;

import com.google.gson.Gson;
import com.monitoring.entity.ScreenShots;
import com.monitoring.service.ManagerService;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.ScreenShotsService;
import com.monitoring.service.UserService;
import com.monitoring.util.MyObject;
import com.monitoring.util.MyTestUtils;
import com.monitoring.util.Pager;
import com.monitoring.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@SessionAttributes(value = "screenshots")
@Controller
public class AppScreenShotsController {
    @Autowired
    ScreenShotsService screenShotsService;
    @Autowired
    MonitoringService monitoringService;
    @Autowired
    ManagerService managerService;
    @Autowired
    UserService userService;


    /**
     * 添加截图
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appAddShots")
    public void appAddShots(HttpServletRequest request, HttpServletResponse response) {
        int flag = 0;
        Gson gson = new Gson();
        String shotsName = request.getParameter("shotsName");
        String shotsURL = request.getParameter("shotsURL");
        Date shotsTime = new Date();
        int monitorId = Integer.parseInt(request.getParameter("monitorId"));
        ScreenShots screenShots = new ScreenShots();
        screenShots.setShotsName(shotsName);
        screenShots.setShotsURL(shotsURL);
        screenShots.setShotsTime(shotsTime);
        screenShots.setMonitor(monitoringService.getMonitoringById(monitorId));
        if (screenShotsService.addScreenShots(screenShots)) {
            flag = 1;
        } else {
            flag = 0;
        }
        String resource = gson.toJson(flag);
        ResponseUtils.renderJson(response, resource);
    }

    /**
     * 用户查看所有的截图
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appToUserScreenShotsListJsp")
    public void toManagerScreenShotsListJsp(HttpServletRequest request, HttpServletResponse response) {
        int flag = 0;
        Gson gson = new Gson();
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        Pager<ScreenShots> pager = screenShotsService.getScreenShotsByUser(pageNum, userId);
        if (pager.getTotalPage() != 0) {
            flag = 1;
        }
        String data = gson.toJson(pager);
        MyObject myObject = new MyObject(flag, data);
        String resource = gson.toJson(myObject);
        ResponseUtils.renderJson(response, resource);
    }

    /**
     * 修改截图信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appUpdateScreenShots")
    public void appUpdateScreenShots(HttpServletRequest request, HttpServletResponse response) {
        int flag = 0;
        Gson gson = new Gson();
        String shotsId = request.getParameter("shotsId");
        String shotsName = request.getParameter("shotsName");
        String shotsURL = request.getParameter("shotsURL");

        ScreenShots screenShots = new ScreenShots();
        screenShots.setShotsId(Integer.parseInt(shotsId));
        screenShots.setShotsName(shotsName);
        screenShots.setShotsURL(shotsURL);

        if (screenShotsService.updateScreenShots(screenShots)) {
            //修改成功
            flag = 1;
        }
        String resource = gson.toJson(flag);
        ResponseUtils.renderJson(response, resource);
    }

    /**
     * 删除截图
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appDeleteScreenShots")
    public void appDeleteScreenShots(HttpServletRequest request,HttpServletResponse response) {
        int flag = 0;
        String shotsId = request.getParameter("shotsId");

        if(screenShotsService.deleteScreenShotsById(Integer.parseInt(shotsId))){
            flag = 1;
        }
        Gson gson = new Gson();
        String  resource = gson.toJson(flag);
        ResponseUtils.renderJson(response,resource);

    }

}
