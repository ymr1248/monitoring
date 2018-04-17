package com.monitoring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.monitoring.dao.MonitorgroupDao;
import com.monitoring.entity.MonitorGroup;

import antlr.collections.impl.IntRange;

@Repository
public class MonitorgroupDaoImpl implements MonitorgroupDao {

	@Resource
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.openSession();
	}

	@Override
	public int add(MonitorGroup monitorGroup) {
		Session session = this.getSession();
		try {
			session.save(monitorGroup);
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public int update(MonitorGroup monitorGroup) {
		Session session = this.getSession();
		int result;
		try {
			result = session.createQuery("update MonitorGroup set groupName=?,groupType=? where groupId=?")
					.setParameter(0, monitorGroup.getGroupName()).setParameter(1, monitorGroup.getGroupType())
					.setParameter(2, monitorGroup.getGroupId()).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int delete(int id) {
		Session session = this.getSession();
		int result;
		try {
			result = session.createQuery("delete MonitorGroup where groupId=?").setParameter(0, id).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int deleteByUserId(int userId) {
		Session session = this.getSession();
		int result;
		try {
			result = session.createQuery("delete MonitorGroup where userId=?").setParameter(0, userId).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public MonitorGroup getMonitorGroupById(int id) {
		Session session = this.getSession();
		MonitorGroup monitorGroup = new MonitorGroup();
		try {
			monitorGroup = (MonitorGroup) session.createQuery("from MonitorGroup where groupId=?").setParameter(0, id)
					.uniqueResult();
		} finally {
			session.close();
		}
		return monitorGroup;
	}

	@Override
	public List<MonitorGroup> getMonitorGroupsByUserId(int userId) {

		Session session = this.getSession();
		List<MonitorGroup> monitorGroups = new ArrayList<>();

		try {
			org.hibernate.Query q = session.createQuery("from MonitorGroup where userId=:userId");
			q.setParameter("userId", userId);
			monitorGroups = q.list();
		} finally {
			session.close();
		}

		return monitorGroups;
	}

	@Override
	public List<MonitorGroup> getMonitorGroups(int userId, String groupName) {
		Session session = this.getSession();
		List<MonitorGroup> monitorGroups = new ArrayList<>();
		try {
			monitorGroups = session.createQuery("from MonitorGroup where userId=? and groupName like ?")
							.setParameter(0, userId).setParameter(1, "%"+groupName+"%").list();
		} finally {
			session.close();
		}
		return monitorGroups;
	}
	
	
	

}
