package com.monitoring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author mry 2018年1月2日 上午10:05:59
 */
@Entity
@Table(name = "video")
public class Video {
	
	//视频id
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//视频大小
	@Column(name = "videoSize", nullable = false, length = 32)
	private String videoSize;
	
	//视频名称
	@Column(name = "videoName", nullable = false, length = 64)
	private String videoName;
	
	//视频url地址
	@Column(name = "videoInformation", nullable = false, length = 200)
	private String videoInformation;
	
	//视频开始时间（北京时间为基准，格式为 2018-01-22 13:12:11）
	@Column(name = "videoStart",nullable = false,length = 32)
	private Date videoStart;
	
	//视频结束时间（北京时间为基准，格式为 2018-01-22 13:12:11）
	@Column(name = "videoEnd",nullable = false,length = 32)
	private Date videoEnd;
	
	//视频时长（字符类型）
	@Column(name = "videoTime",nullable = false,length = 64)
	private String videoTime;
	
	//视频入库时间（北京时间为基准，格式为 2018-01-22 13:12:11）
	@Column(name = "videoDate",nullable = false,length = 32)
	private Date videoDate;
	
	//机器id
	@ManyToOne(targetEntity = Monitoring.class)
	@JoinColumn(name = "MonitoringId",nullable = false)
	private Monitoring monitoring;
	
	//用户id
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userId",nullable = false)
	private User user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoInformation() {
		return videoInformation;
	}
	public void setVideoInformation(String videoInformation) {
		this.videoInformation = videoInformation;
	}
	public Date getVideoDate() {
		return videoDate;
	}
	public void setVideoDate(Date videoDate) {
		this.videoDate = videoDate;
	}
	public Monitoring getMonitoring() {
		return monitoring;
	}
	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getVideoStart() {
		return videoStart;
	}
	public void setVideoStart(Date videoStart) {
		this.videoStart = videoStart;
	}
	public Date getVideoEnd() {
		return videoEnd;
	}
	public void setVideoEnd(Date videoEnd) {
		this.videoEnd = videoEnd;
	}
	public String getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}
	@Override
	public String toString() {
		return "Video [id=" + id + ", videoSize=" + videoSize + ", videoName=" + videoName + ", videoInformation="
				+ videoInformation + ", videoStart=" + videoStart + ", videoEnd=" + videoEnd + ", videoTime="
				+ videoTime + ", videoDate=" + videoDate + ", monitoring=" + monitoring + ", User=" + user + "]";
	}
	
	
}
