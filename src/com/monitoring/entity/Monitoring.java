package com.monitoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * @author mry
 *2018年1月2日 上午10:06:33
 */

@Entity
@Table(name="monitoring")
public class Monitoring {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//摄像头名称
	@Column(name = "monitorName",nullable = false,length = 16)
	private String monitorName;
	//摄像头Ip地址
	@Column(name = "monitorIp",nullable = false, length = 32)
	private String monitorIp;
	//设备型号
	@Column(name = "monitorType",nullable = true,length = 32)
	private String monitorType;
	//摄像头信息
	@Column(name = "monitorInfo",nullable = true,length =500)
	private String monitorInfo;
	//摄像头安装位置
	@Column(name = "monitorLocation",nullable = false, length = 32)
	private String monitorLocation;
	//摄像头安装时间
	@Column(name = "locatedTime",nullable = false,length = 32)
	private Date locatedTime;
	//摄像头状态1为打开0为关闭-1故障
	@Column(name = "monitorStatus",nullable = false,length = 8)
	private int monitorStatus;
	//录制视频存储位置
	@Column(name = "storageLocation",nullable = false,length = 32)
	private String storageLocation;
	//摄像头分组
	@ManyToOne(targetEntity = MonitorGroup.class)
	@JoinColumn(name = "groupId")
	private MonitorGroup monitorGroup;
	//摄像头用户
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userId")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public String getMonitorIp() {
		return monitorIp;
	}

	public void setMonitorIp(String monitorIp) {
		this.monitorIp = monitorIp;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getMonitorInfo() {
		return monitorInfo;
	}

	public void setMonitorInfo(String monitorInfo) {
		this.monitorInfo = monitorInfo;
	}

	public String getMonitorLocation() {
		return monitorLocation;
	}

	public void setMonitorLocation(String monitorLocation) {
		this.monitorLocation = monitorLocation;
	}

	public Date getLocatedTime() {
		return locatedTime;
	}

	public void setLocatedTime(Date locatedTime) {
		this.locatedTime = locatedTime;
	}

	public int getMonitorStatus() {
		return monitorStatus;
	}

	public void setMonitorStatus(int monitorStatus) {
		this.monitorStatus = monitorStatus;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public MonitorGroup getMonitorGroup() {
		return monitorGroup;
	}

	public void setMonitorGroup(MonitorGroup monitorGroup) {
		this.monitorGroup = monitorGroup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Monitoring{" +
				"id=" + id +
				", monitorName='" + monitorName + '\'' +
				", monitorIp='" + monitorIp + '\'' +
				", monitorType='" + monitorType + '\'' +
				", monitorInfo='" + monitorInfo + '\'' +
				", monitorLocation='" + monitorLocation + '\'' +
				", locatedTime=" + locatedTime +
				", monitorStatus=" + monitorStatus +
				", storageLocation='" + storageLocation + '\'' +
				", monitorGroup=" + monitorGroup +
				", user=" + user +
				'}';
	}
}
