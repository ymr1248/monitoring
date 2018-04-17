package com.monitoring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monitoring.dao.MonitorMessageDao;
import com.monitoring.entity.MonitorMessages;
import com.monitoring.service.MonitorMessageService;

@Service
public class MonitorMessageServiceImpl implements MonitorMessageService {

	@Autowired
	MonitorMessageDao monitorMessageDao;
	
	@Override
	public int add(MonitorMessages monitorMessages) {
		return monitorMessageDao.add(monitorMessages);
	}

	@Override
	public int delete(int id) {
		return monitorMessageDao.delete(id);
	}

	@Override
	public MonitorMessages getMonitorMessageById(int id) {
		return monitorMessageDao.getMonitorMessageById(id);
	}

	@Override
	public List<MonitorMessages> getMonitorMessagesByMonitorId(int monitorId) {
		return monitorMessageDao.getMonitorMessagesByMonitorId(monitorId);
	}

	@Override
	public int delete(int[] id) {
		for(int i=0;i<id.length;i++){
			this.delete(id[i]);
		}
		return 1;
	}
	
	

}
