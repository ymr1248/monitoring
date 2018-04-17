package com.monitoring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monitoring.dao.MonitorgroupDao;
import com.monitoring.entity.MonitorGroup;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.util.Pager;

@Service
public class MonitorgroupServiceImpl implements MonitorgroupService {

	@Autowired
	MonitorgroupDao monitorgroupDao;

	@Override
	public int add(MonitorGroup monitorGroup) {
		return monitorgroupDao.add(monitorGroup);
	}

	@Override
	public int update(MonitorGroup monitorGroup) {

		return monitorgroupDao.update(monitorGroup);
	}

	@Override
	public int delete(int id) {
		return monitorgroupDao.delete(id);
	}
	
	@Override
	public int deleteByUserId(int userId) {
		return monitorgroupDao.deleteByUserId(userId);
	}

	@Override
	public MonitorGroup getMonitorGroupById(int id) {
		return monitorgroupDao.getMonitorGroupById(id);
	}

	@Override
	public Pager<MonitorGroup> getMonitorGroups(int pageNum,int userId) {
		List<MonitorGroup> list = monitorgroupDao.getMonitorGroupsByUserId(userId);
		Pager<MonitorGroup> pager = new Pager<MonitorGroup>(pageNum,8,list);
		return pager;
	}

	@Override
	public Pager<MonitorGroup> getMonitorGroups(List<MonitorGroup> list, int pageNum) {
		Pager<MonitorGroup> pager = new Pager<MonitorGroup>(pageNum,8,list);
		return pager;
	}

	@Override
	public List<MonitorGroup> getMonitorGroupsByUserId(int userId) {
		return monitorgroupDao.getMonitorGroupsByUserId(userId);
	}

	@Override
	public List<MonitorGroup> getMonitorGroups(int userId, String groupName) {
		return monitorgroupDao.getMonitorGroups(userId, groupName);
	}
	
	
	
	
	
	
	
}
