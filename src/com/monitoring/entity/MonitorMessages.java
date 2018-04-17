package com.monitoring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="monitormessages")
public class MonitorMessages {

	//监控器消息id
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//监控器消息内容
	@Column(name = "monitorMessage",nullable = false,length = 64)
	private String monitorMessage;
	
	//监控器id
	@Column(name = "monitorId",nullable = false)
	private int monitorId;
	
	//消息时间
	@Column(name = "createtime",nullable = false)
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonitorMessage() {
		return monitorMessage;
	}

	public void setMonitorMessage(String monitorMessage) {
		this.monitorMessage = monitorMessage;
	}

	public int getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(int monitorId) {
		this.monitorId = monitorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
