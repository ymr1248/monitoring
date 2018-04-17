package com.monitoring.service;

import java.util.List;

import com.monitoring.entity.MonitorGroup;
import com.monitoring.util.Pager;

public interface MonitorgroupService {

	//添加监控器组
	public int add(MonitorGroup monitorGroup);
	
	//更新监控器组
	public int update(MonitorGroup monitorGroup);
	
	//删除监控器组
	public int delete(int id);
	
	//通过用户id删除监控器组，用于删除用户
	public int deleteByUserId(int userId);
	
	//通过id获取监控器组
	public MonitorGroup getMonitorGroupById(int id);
	 
	//通过页码和用户id获取pager
	public Pager<MonitorGroup> getMonitorGroups(int pageNum,int userId);

	//通过用户id获取监控器组
	public List<MonitorGroup> getMonitorGroupsByUserId(int userId);
	
	//通过数据列表和页码获取pager
	public Pager<MonitorGroup> getMonitorGroups(List<MonitorGroup> list,int pageNum);
	
	//模糊查询，通过名称和用户id获取监控器组
	List<MonitorGroup> getMonitorGroups(int userId, String groupName);
	
}
