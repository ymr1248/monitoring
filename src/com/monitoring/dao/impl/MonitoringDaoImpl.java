package com.monitoring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.monitoring.dao.MonitoringDao;
import com.monitoring.entity.Monitoring;
@Repository
public class MonitoringDaoImpl implements MonitoringDao{
	@Resource
	private SessionFactory sessionFactory;
	private Session getSession() {
		return sessionFactory.openSession();
	}
//	Session session=this.getSession();
	
	@Override
	public int delMonitoring(int id) {
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete Monitoring where id=?").setParameter(0, id).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public int delMonitoringByUserId(int userId) {
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete Monitoring where userId=?").setParameter(0, userId).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int update(Monitoring monitoring) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add(Monitoring monitoring) {
		Session session = this.getSession();
		try {
			session.save(monitoring);
		} finally {
			session.close();
		}
		return 1;

	}




	@Override
	public Monitoring getMonitoringById(int id) {
		Session session = this.getSession();
		Monitoring monitoring = new Monitoring();
		try {
			monitoring = (Monitoring) session.createQuery("from Monitoring where id = ?").setParameter(0, id).uniqueResult();
		} finally {
			session.close();
		}
		return monitoring;
	}

	@Override
	public List<Monitoring> getMonitorings() {
		Session session = this.getSession();
		List<Monitoring> list = new ArrayList<Monitoring>();
		try {
			list = session.createCriteria(Monitoring.class).list();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List<Monitoring> getMonitorByUserId(int userId) {
		Session session = this.getSession();
		List<Monitoring> monitoringList = new ArrayList<>();
		try {
			org.hibernate.Query q = session.createQuery("from Monitoring where userId=:userId");
			q.setParameter("userId", userId);
			monitoringList = q.list();
		} finally {
			session.close();
		}

		return monitoringList;
	}

	@Override
	public int updateMonitorByAddition(Monitoring mointor) {
		Session session = this.getSession();
		Integer result = 0;
		try {
			List<Object> objects= new ArrayList<Object>();
			String hql;

			objects.add(mointor.getMonitorIp());
			objects.add(mointor.getMonitorName());
			objects.add(mointor.getMonitorInfo());
			objects.add(mointor.getMonitorLocation());
			objects.add(mointor.getMonitorStatus());
			objects.add(mointor.getStorageLocation());
			objects.add(mointor.getMonitorGroup().getGroupId());
			objects.add(mointor.getMonitorType());
			hql = "update Monitoring set monitorIp = ?,monitorName=?,monitorInfo=?,monitorLocation=?,monitorStatus=?,storageLocation=?,groupId=?,monitorType=? where id=?";
			objects.add(mointor.getId());
			org.hibernate.Query query = session.createQuery(hql)
					.setParameter(0, objects);
			if (objects!=null&&objects.size()>0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			result = query.executeUpdate();
		}finally {
			session.close();
		}
		return result;
	}

	@Override
	public List<Monitoring> getMonitors(int userId, int groupId) {
		Session session = this.getSession();
		List<Monitoring> monitoringList = new ArrayList<>();
		try {
			monitoringList = session.createQuery("from Monitoring where userId = ? and groupId = ?").setParameter(0, userId).setParameter(1, groupId).list();
			
		} finally {
			session.close();
		}
		return monitoringList;
	}
	
	@Override
	public List<Monitoring> getMonitors(int userId, int groupId,String monitorName) {
		Session session = this.getSession();
		List<Monitoring> monitoringList = new ArrayList<>();
		try {
			monitoringList = session.createQuery("from Monitoring where userId = ? and groupId = ? and monitorName like ?")
							.setParameter(0, userId).setParameter(1, groupId).setParameter(2, "%"+monitorName+"%").list();
		} finally {
			session.close();
		}
		return monitoringList;
	}

	@Override
	public List<Monitoring> getMonitors(int userId,String monitorName) {
		Session session = this.getSession();
		List<Monitoring> monitoringList = new ArrayList<>();
		try {
			monitoringList = session.createQuery("from Monitoring where userId = ? and monitorName like ?")
							.setParameter(0, userId).setParameter(1, "%"+monitorName+"%").list();
		} finally {
			session.close();
		}
		return monitoringList;
	}
	
	@Override
	public int alterGroupId(int id, int groupId) {
		Session session = this.getSession();
		int result;
		try {
			result=session.createQuery("update Monitoring set groupId = ? where id=?")
					.setParameter(0, groupId).setParameter(1, id).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}

	

}
