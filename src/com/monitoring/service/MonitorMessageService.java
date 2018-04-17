package com.monitoring.service;

import java.util.List;
import com.monitoring.entity.MonitorMessages;

public interface MonitorMessageService {

	//添加监控器消息
	int add(MonitorMessages monitorMessages);
		
	//删除监控器消息
	int delete(int id);
		
	//通过id获取消息
	MonitorMessages getMonitorMessageById(int id);
		
	//通过监控器id获取消息列表
	List<MonitorMessages> getMonitorMessagesByMonitorId(int monitorId);
	
	int delete(int[] id);
}
