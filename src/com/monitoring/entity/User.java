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

/**
 * @author mry 2018年1月2日 上午8:44:24
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 登录账号
	@Column(name = "userAccount", nullable = false, length = 16, unique = true)
	private String userAccount;
	//用户地址
	@Column(name = "userAddress",nullable = true, length = 32)
	private String userAddress;
	//用户电话
	@Column(name = "userPhone",nullable = false,length = 16,unique = true)
	private String userPhone;
	//用户e-mail
	@Column(name = "userEMail",nullable = true,length = 32)
	private String userEMail;
	//用户头像地址
	@Column(name = "avatar",nullable = true,length = 200)
	private String avatar;
	//用户密码
	@Column(name = "password", nullable = false, length = 64)
	private String password;
	//用户名
	@Column(name = "userName", nullable = false, length = 16)
	private String userName;
	@Column(name = "appId",nullable = true,length = 200)
	private String appId;
	// 用户等级
	@ManyToOne(targetEntity = UserGrade.class)
	@JoinColumn(name = "userGradeId")
	private UserGrade UserGrade;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserEMail() {
		return userEMail;
	}
	public void setUserEMail(String userEMail) {
		this.userEMail = userEMail;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public UserGrade getUserGrade() {
		return UserGrade;
	}
	public void setUserGrade(UserGrade userGrade) {
		UserGrade = userGrade;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userAccount=" + userAccount + ", userAddress=" + userAddress + ", userPhone="
				+ userPhone + ", userEMail=" + userEMail + ", avatar=" + avatar + ", password=" + password
				+ ", userName=" + userName + ", UserGrade=" + UserGrade + ",appId="+appId + "]";
	}
	

}
