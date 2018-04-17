package com.monitoring.appcontroller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.monitoring.entity.Monitoring;
import com.monitoring.entity.Space;
import com.monitoring.entity.User;
import com.monitoring.entity.Video;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.SpaceService;
import com.monitoring.service.UserService;
import com.monitoring.service.VideoService;
import com.monitoring.util.MyObject;
import com.monitoring.util.Pager;
import com.monitoring.util.ResponseUtils;
import com.monitoring.util.TimeUtil;

@Controller
public class AppVideoController {
	
	@Autowired
	MonitorgroupService monitorgroupService;
	@Autowired
	MonitoringService monitoringService;
	@Autowired
	UserService userService;
	@Autowired
	VideoService videoService;
	@Autowired
	SpaceService spaceService;
	
	//视频列表
	@RequestMapping(value = "/appVideoList")
	public void AppVideoList(HttpServletRequest request, HttpServletResponse response) {
		int userId = Integer.parseInt(request.getParameter("userId"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int monitorId = Integer.parseInt(request.getParameter("monitoringId"));
		int flag=0;
		Monitoring monitoring = monitoringService.getMonitoringById(monitorId);
		User user = userService.getUserById(userId);
		String data;
		Gson gson = new Gson();
		if(request.getParameter("startTime")!=null && request.getParameter("endTime")!=null
				&& !request.getParameter("startTime").equals("") && !request.getParameter("endTime").equals("")){
			String start = request.getParameter("startTime");
			String end = request.getParameter("endTime");
			Date startTime = TimeUtil.stringToDate(start);
			Date endTime = TimeUtil.stringToDate(end);
			List<Video> videos = videoService.getVideos(user,monitoring,startTime,endTime);
			Pager<Video> pager = new Pager<Video>(pageNum, 10, videos);
			data = gson.toJson(pager);
			if(pager.getTotalPage()>0){
				flag = 1;
			}
		}else {
			List<Video> videos = videoService.getVideos(user,monitoring);
			Pager<Video> pager = new Pager<Video>(pageNum, 10, videos);
			data = gson.toJson(pager);
			if(pager.getTotalPage()>0){
				flag = 1;
			}
		}
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	//视频详情
	@RequestMapping(value = "/appVideo")
	public void AppVideo(int id,HttpServletRequest request, HttpServletResponse response) {
		Video video = videoService.getVideoById(id);
		int flag = 0;
		if(video!=null){
			flag = 1;
		}
		Gson gson = new Gson();
		String data = gson.toJson(video);
		MyObject myObject = new MyObject(flag, data);
		String json = gson.toJson(myObject);
		ResponseUtils.renderJson(response, json);
	}
	
	
	
}
