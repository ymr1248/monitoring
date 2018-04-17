/**
 * 
 */
package com.monitoring.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Administrator 2017年12月27日
 *
 */
@Entity
@Table(name = "userrelation")
public class UserRelation {
	@Id
	@Column(name = "userRelationId", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userRelationId;
	//上级
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userId",nullable = false)
	private User user;
	//下级
	@ManyToOne(targetEntity = Operator.class)
	@JoinColumn(name = "operatorId",nullable = false)
	private Operator operator;

	public int getUserRelationId() {
		return userRelationId;
	}

	public void setUserRelationId(int userRelationId) {
		this.userRelationId = userRelationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "UserRelation{" +
				"userRelationId=" + userRelationId +
				", user=" + user +
				", operator=" + operator +
				'}';
	}
}
