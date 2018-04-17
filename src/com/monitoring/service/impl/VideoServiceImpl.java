package com.monitoring.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monitoring.dao.VideoDao;
import com.monitoring.entity.Monitoring;
import com.monitoring.entity.User;
import com.monitoring.entity.Video;
import com.monitoring.service.VideoService;
import com.monitoring.util.Pager;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	VideoDao videoDao;

	@Override
	public int add(Video video) {
		return videoDao.add(video);
	}

	@Override
	public int update(Video video) {
		return videoDao.update(video);
	}

	@Override
	public int delVideo(int id) {
		return videoDao.delVideo(id);
	}
	
	@Override
	public int delVideoByUserId(int id) {
		return videoDao.delVideoByUserId(id);
	}

	@Override
	public Video getVideoById(int id) {
		return videoDao.getVideoById(id);
	}

	@Override
	public List<Video> getVideos(User user) {
		return videoDao.getVideos(user);
	}

	@Override
	public List<Video> getVideos(User user, Monitoring monitoring) {
		return videoDao.getVideos(user, monitoring);
	}

	@Override
	public List<Video> getVideos(User user, Monitoring monitoring, Date startTime, Date endTime) {
		return videoDao.getVideos(user, monitoring, startTime, endTime);
	}

	@Override
	public Pager<Video> getVideosByPageNum(List<Video> list, int pageNum) {
		Pager<Video> pager = new Pager<Video>(pageNum, 1, list);
		return pager;
	}

	
	
}
