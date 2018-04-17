package com.monitoring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.monitoring.dao.MonitorMessageDao;
import com.monitoring.entity.MonitorMessages;
import com.monitoring.entity.Monitoring;

@Repository
public class MonitorMessageImpl implements MonitorMessageDao {

	@Resource
    private SessionFactory sessionFactory;
    private Session getSession() {
        return sessionFactory.openSession();
    }
	
	@Override
	public int add(MonitorMessages monitorMessages) {
		Session session = this.getSession();
		try {
			session.save(monitorMessages);
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public int delete(int id) {
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete MonitorMessages where id=?").setParameter(0, id).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public MonitorMessages getMonitorMessageById(int id) {
		Session session = this.getSession();
		MonitorMessages message = new MonitorMessages();
		try {
			message = (MonitorMessages) session.createQuery("from MonitorMessages where id = ?").setParameter(0, id).uniqueResult();
		} finally {
			session.close();
		}
		return message;
	}

	@Override
	public List<MonitorMessages> getMonitorMessagesByMonitorId(int monitorId) {
		Session session = this.getSession();
		List<MonitorMessages> list = new ArrayList<MonitorMessages>();
		try {
			list = session.createQuery("from MonitorMessages where monitorId = ?").setParameter(0, monitorId).list();
		} finally {
			session.close();
		}
		return list;
	}

}
