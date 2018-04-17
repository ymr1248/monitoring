/**
 *
 */
package com.monitoring.controller;

import com.google.gson.Gson;
import com.monitoring.entity.Space;
import com.monitoring.entity.SpaceOrder;
import com.monitoring.entity.User;
import com.monitoring.service.SpaceService;
import com.monitoring.service.UserService;
import com.monitoring.util.MyTestUtils;
import com.monitoring.util.Pager;
import com.monitoring.util.ResponseUtils;
import com.monitoring.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author Lee 2017年12月18日
 */
@Controller
public class SpaceController {
    @Autowired
    SpaceService spaceService;
    @Autowired
    UserService userService;

    /**
     * 获取用户信息跳转到添加空间页面
     *
     * @param request
     * @param model
     * @return 页面切换获取数据
     */
    @RequestMapping(value = "/toAddSpaceJsp")
    public String toAddSpaceJsp(HttpServletRequest request, ModelMap model) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        //获取用户
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "space_add";
    }

    /**
     * 添加空间
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addSpace")
    public String addSpace(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        String spaceName = request.getParameter("spaceName");
        String spaceSize = request.getParameter("spaceSize");
        int userId = Integer.parseInt(request.getParameter("userId"));
        Space space = new Space();
        space.setSpaceName(spaceName);
        space.setSpaceSize(spaceSize);
        space.setUseSize("0");
        space.setExtraSize("0");
        space.setUser(userService.getUserById(userId));
        spaceService.addSpace(space);
        return "redirect:/toSpaceUseListJsp?pageNum=1";

    }

    /**
     * 跳转到空间使用信息页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toSpaceUseListJsp")
    public String toSpaceUseListJsp(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Pager<Space> pager = spaceService.getSpacesPageList(pageNum);
        modelMap.addAttribute("spaceList", pager.getDataList());
        modelMap.addAttribute("totalPage", pager.getTotalPage());
        modelMap.addAttribute("pageNum", pager.getCurrentPage());
        return "space_uselist";

    }

    /**
     * 跳转到空间充值订单信息页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toSpaceOrderListJsp")
    public String toSpaceOrderListJsp(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Pager<SpaceOrder> pager = spaceService.getSpacesOrderPageList(pageNum);
        System.out.println("spaceOrderList===================="+pager.getDataList());
        modelMap.addAttribute("spaceOrderList", pager.getDataList());
        modelMap.addAttribute("totalPage", pager.getTotalPage());
        modelMap.addAttribute("pageNum", pager.getCurrentPage());
        return "space_orderlist";
    }

    /**
     * 获取用户信息跳转到添加空间充值订单页面
     *
     * @param request
     * @param model
     * @return 页面切换获取数据
     */
    @RequestMapping(value = "/toAddSpaceOrderJsp")
    public String toAddSpaceOrderJsp(HttpServletRequest request, ModelMap model) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        //获取用户
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "spaceorder_add";
    }

    /**
     * 添加空间充值订单
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addSpaceOrder")
    public String addSpaceOrder(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        String extraMoths = request.getParameter("extraMoths");
        String spaceSize = request.getParameter("spaceSize");
        int userId = Integer.parseInt(request.getParameter("userId"));
        SpaceOrder spaceOrder = new SpaceOrder();
        spaceOrder.setExtraSize(spaceSize);
        spaceOrder.setExtraMoths(Integer.parseInt(extraMoths));
        spaceOrder.setStartTime(TimeUtil.dateToStrNoTime(new Date()));
        spaceOrder.setSpace(spaceService.getSpaceByUserId(userId));
        spaceService.addSpaceOrder(spaceOrder);
        return "redirect:/toSpaceOrderListJsp?pageNum=1";

    }

    /**
     * 查看空间使用情况,返回json数据
     *
     * @param request
     * @param modelMap
     * @return 页面切换获取数据
     */
    @RequestMapping(value = "/spaceUseList")
    public void spaceUseList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        int pageNum = 1;
        List<Space> spaceListRes = spaceService.getSpaces();
        Pager<Space> pager = new Pager<>(pageNum, 10, spaceListRes);

        Gson gson = new Gson();
        String s = gson.toJson(pager);
        System.out.println(s);
        ResponseUtils.renderJson(response, s);
    }

    /**
     * 根据ID跳转到修改空间使用信息页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUpdateSpaceJsp")
    public String toUpdateSpaceJsp(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        int spaceId = Integer.parseInt(request.getParameter("spaceId"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));//页码
        Space space = spaceService.getSpaceById(spaceId);
        modelMap.addAttribute("space", space);
        modelMap.addAttribute("pageNum", pageNum);
        return "space_update";
    }

    /**
     * 修改空间使用信息
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateSpace")
    public String updateSpaceInfo(HttpServletRequest request, ModelMap modelMap) {
        if (MyTestUtils.isLogined(request) != null) {
            return "page_403";
        }
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        String spaceId = request.getParameter("spaceId");
        String spaceName = request.getParameter("spaceName");
        String spaceSize = request.getParameter("spaceSize");
        String useSize = request.getParameter("useSize");

        Space space = new Space();
        space.setId(Integer.parseInt(spaceId));
        space.setSpaceName(spaceName);
        space.setSpaceSize(spaceSize);
        space.setUseSize(useSize);
        if (spaceService.updateSpace(space)) {
            //修改成功
            return "redirect:/toSpaceUseListJsp?pageNum=" + pageNum;
        }
        modelMap.put("reason", "操作失败，请确认操作！");
        return "page_400";


    }


    @RequestMapping(value = "/deleteSpace")
    public String delSpaceById(HttpServletRequest request, ModelMap modelMap) {

        return "space";
    }

}
