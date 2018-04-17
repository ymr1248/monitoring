package com.monitoring.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.monitoring.entity.Monitoring;
import com.monitoring.entity.User;
import com.monitoring.entity.Video;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.UserService;
import com.monitoring.service.VideoService;
import com.monitoring.util.MyTestUtils;
import com.monitoring.util.Pager;
import com.monitoring.util.TimeUtil;

@Controller
public class VideoController {

	@Autowired
	VideoService videoService;
	@Autowired 
	UserService userService;
	@Autowired
	MonitoringService monitoringService;
	
	/*
	 * 前往视频添加页面，在此系统视频添加只需系统自动处理即可
	 */
	@RequestMapping(value="/toVideo_add")
	public String toVideo_add(HttpServletRequest request,ModelMap modelMap){
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		List<User> list1 = userService.getUsers();
		List<Monitoring> list2 = monitoringService.getMonitorings();
		modelMap.addAttribute("userList", list1);
		modelMap.addAttribute("monitoringList", list2);	
		return "userviews/video_add";
	}
	
	/*
	 * 添加视频
	 */
	@RequestMapping(value="/video_add")
	public String video_add(Video video,HttpServletRequest request,ModelMap modelMap){
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		Date videoStart = TimeUtil.stringToDate(request.getParameter("videoStart1"));
		Date videoEnd = TimeUtil.stringToDate(request.getParameter("videoEnd1"));
		User user = userService.getUserById(Integer.parseInt(request.getParameter("userId1")));
		Monitoring monitoring = monitoringService.getMonitoringById(Integer.parseInt(request.getParameter("MonitoringId1")));
		video.setUser(user);
		video.setMonitoring(monitoring);
		video.setVideoStart(videoStart);
		video.setVideoEnd(videoEnd);
		video.setVideoDate(new Date());
		videoService.add(video);
		return "userviews/user_index";
	}
	
	/*
	 * 前往视频列表页面
	 */
	@RequestMapping(value="/toVideo_list")
	public String toVideo_list(HttpServletRequest request,ModelMap modelMap){
		if (MyTestUtils.userIsLogined(request) != null) {
			return "page_403";
		}
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int monitoringId = Integer.parseInt(request.getParameter("monitoringId"));
		Monitoring monitoring = monitoringService.getMonitoringById(monitoringId);
		User user = (User)request.getSession().getAttribute("USER");
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
			return "userviews/video_list";
		}
		Pager<Video> pager = videoService.getVideosByPageNum(videoService.getVideos(user,monitoring), pageNum);
		modelMap.addAttribute("videoList", pager.getDataList());
		modelMap.addAttribute("totalPage", pager.getTotalPage());
		modelMap.addAttribute("pageNum", pageNum);
		modelMap.addAttribute("monitoringId", monitoringId);
		return "userviews/video_list";
	} 
	
	/*
	 * 播放视频
	 */
	@RequestMapping(value="/toVideo_play")
	public String toVideo_play(int id,HttpServletRequest request,ModelMap modelMap){
		Video video = videoService.getVideoById(id);
		modelMap.addAttribute("video", video);
		return "userviews/video_play";
	}	
	
	
}
