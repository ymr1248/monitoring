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
 * @author mry
 *2018年1月2日 上午10:06:10
 */
@Entity
@Table(name="space")
public class Space {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//初始空间大小(g)
	@Column(name = "spaceSize", nullable = false, length = 32)
	private String spaceSize;
	//已用大小
	@Column(name = "useSize", nullable = false, length = 32)
	private String useSize;
	//额外新增大小
	@Column(name = "extraSize", nullable = false, length = 32)
	private String extraSize;
	//空间名称
	@Column(name = "spaceName", nullable = false, length = 32)
	private String spaceName;

	//权限
//	@Column(name = "permission",nullable = false)
//	private int permission;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userId")
	private User user;

	public int getId() {
		return id;
	}

	public String getUseSize() {
		return useSize;
	}

	public void setUseSize(String useSize) {
		this.useSize = useSize;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpaceSize() {
		return spaceSize;
	}

	public void setSpaceSize(String spaceSize) {
		this.spaceSize = spaceSize;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getExtraSize() {
		return extraSize;
	}

	public void setExtraSize(String extraSize) {
		this.extraSize = extraSize;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Space{" +
				"id=" + id +
				", spaceSize='" + spaceSize + '\'' +
				", useSize='" + useSize + '\'' +
				", extraSize='" + extraSize + '\'' +
				", spaceName='" + spaceName + '\'' +
				", user=" + user +
				'}';
	}
}
