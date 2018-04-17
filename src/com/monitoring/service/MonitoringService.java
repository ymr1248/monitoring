package com.monitoring.service;

import java.util.List;

import com.monitoring.entity.Monitoring;
import com.monitoring.util.Pager;

/**
 * @author mry
 *2018年1月2日 上午11:20:22
 */
public interface MonitoringService {
		// 保存或更新
		public int updateOrsave(Monitoring monitoring);

		// 根据ID删除用户
		public boolean delMonitoring(int id);
		
		// 根据ID删除用户
		public boolean delMonitoringByUserId(int userId);
		
		//
		public int add(Monitoring monitoring);

		// 根据ID查询用户
		public Monitoring getMonitoringById(int id);

		// 查询所有用户
		public List<Monitoring> getMonitorings();
		
		//根据用户ID返回监控器
		public Pager<Monitoring> getMonitorByUserId(int pageNum, int userId);
		
		//更新监控器信息
		public boolean updateMonitorByAddition(Monitoring monitoring);
		
		//返回全部用户名下监控器
		public List<Monitoring> getMonitoringsByUserId(int userId);
		
		//通过监控器组id
		public List<Monitoring> getMonitors(int userId,int groupId);
		
		//通过数据、页码获取pager
		Pager<Monitoring> getMonitors(List<Monitoring> list,int pageNum);
		
		//通过用户id、组id、监控器名称获取监控器列表
		public List<Monitoring> getMonitors(int userId,int groupId,String monitorName);
		
		//通过用户id和监控器名称获取监控器列表
		List<Monitoring> getMonitors(int userId,String monitorName);
		
		
		int alterGroupId(int userId,int[] groupid,int groupId);
		
		int alterGroupId(int id,int groupId);
		
}
