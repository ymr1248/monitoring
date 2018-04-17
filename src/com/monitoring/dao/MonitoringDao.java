package com.monitoring.dao;

import java.util.List;

import com.monitoring.entity.Monitoring;

/**
 * @author mry 2018年1月2日 上午11:18:38
 */
public interface MonitoringDao {
	// 保存或更新
	public int update(Monitoring monitoring);

	public int add(Monitoring monitoring);
	
	//根据ID删除监控器
	public int delMonitoring(int id);
	
	//根据ID删除监控器
	public int delMonitoringByUserId(int userId);

	// 根据ID查找所有监控器
	public Monitoring getMonitoringById(int id);

	// 查询所有监控器
	public List<Monitoring> getMonitorings();
	//通过用户ID查找所有的监控器
	public List<Monitoring> getMonitorByUserId(int userId);

	public int updateMonitorByAddition(Monitoring mointor);
	
	public List<Monitoring> getMonitors(int userId,int groupId);
	
	List<Monitoring> getMonitors(int userId, int groupId,String monitorName);
	
	List<Monitoring> getMonitors(int userId, String monitorName);
	
	int alterGroupId(int id,int groupId);
	
	
}
