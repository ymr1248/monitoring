package com.monitoring.service;

import java.util.Date;
import java.util.List;

import com.monitoring.entity.Monitoring;
import com.monitoring.entity.User;
import com.monitoring.entity.Video;
import com.monitoring.util.Pager;

/**
 * @author mry 2018年1月2日 上午11:25:15
 */
public interface VideoService {
	// 保存
	public int add(Video video);

	// 更新
	public int update(Video video);

	// 根据ID删除视频
	public int delVideo(int id);

	// 根据ID删除视频
	public int delVideoByUserId(int id);
	
	// 根据ID查询视频
	public Video getVideoById(int id);

	// 返回userId所有视频
	public List<Video> getVideos(User user);
	
	//返回userId下monitoringId的所有视频
	public List<Video> getVideos(User user,Monitoring monitoring);
	
	//返回某个机器的startTime到endTime的视频
	public List<Video> getVideos(User user,Monitoring monitoring,Date startTime,Date endTime);
	
	public Pager<Video> getVideosByPageNum(List<Video> list,int pageNum);
}
