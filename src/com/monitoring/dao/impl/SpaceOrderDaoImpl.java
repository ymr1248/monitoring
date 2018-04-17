package com.monitoring.dao.impl;

import com.monitoring.dao.SpaceDao;
import com.monitoring.dao.SpaceOrderDao;
import com.monitoring.entity.Space;
import com.monitoring.entity.SpaceOrder;
import com.monitoring.entity.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mry
 *2018年1月2日 上午11:23:02
 */
@Repository
public class SpaceOrderDaoImpl implements SpaceOrderDao {
	@Resource
	SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.openSession();
	}

	@Override
	public SpaceOrder getSpaceOrderById(int id) {
		Session session = this.getSession();
		SpaceOrder SpaceOrder = new SpaceOrder();
		try {
			SpaceOrder = (SpaceOrder) session.createQuery("from SpaceOrder where id = ?").setParameter(0, id).uniqueResult();
		} finally {
			session.close();
		}
		return SpaceOrder;
	}

	@Override
	public List<SpaceOrder> getSpaceOrderBySpace(Space space) {
		Session session = this.getSession();
		List<SpaceOrder> list = new ArrayList<SpaceOrder>();
		try {
			list = session.createQuery("from SpaceOrder where space = ?").setParameter(0, space).list();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public SpaceOrder getSpaceOrderByName(String name) {
		Session session = this.getSession();
		SpaceOrder SpaceOrder = new SpaceOrder();
		try {
			SpaceOrder = (SpaceOrder) session.createQuery("from SpaceOrder where SpaceOrderName = ?").setParameter(0, name).uniqueResult();
		} finally {
			session.close();
		}
		return SpaceOrder;
	}

	@Override
	public void addSpaceOrder(SpaceOrder SpaceOrder) {
		Session session = this.getSession();
		try {
			session.save(SpaceOrder);
		} finally {
			session.close();
		}
	}

	@Override
	public int deleteSpaceOrderById(int id) {
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete from SpaceOrder where id = ?").setParameter(0, id).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public int deleteSpaceOrderBySpaceId(int spaceId) {
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete from SpaceOrder where spaceId = ?").setParameter(0, spaceId).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public void saveOrupdate(SpaceOrder SpaceOrder) {
		Session session = this.getSession();
		try {
			session.saveOrUpdate(SpaceOrder);
		} finally {
			session.close();
		}
	}

	@Override
	public List<SpaceOrder> getSpaceOrders() {
		Session session = this.getSession();
		List<SpaceOrder> list =  new ArrayList<SpaceOrder>();
		try {
			list =  session.createCriteria(SpaceOrder.class).list();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * 获取分页数据
	 * @return
	 */
	@Override
	public List<SpaceOrder> getSpaceOrdersPageList(int pageNum) {
		Session session = this.getSession();
		Query q = session.createQuery("from SpaceOrder");
		List<SpaceOrder> SpaceOrderList =  new ArrayList<SpaceOrder>();
		try {
			int num = 10;
			int begin = (pageNum - 1) * num;
			try {
				SpaceOrderList= q.setFirstResult(begin).setMaxResults(num).list();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			session.close();
		}
		return SpaceOrderList;
	}

	/**
	 * 获得页数
	 * @return
	 */
	@Override
	public int pageS() {
		Session session = this.getSession();
		Query q = session.createQuery("from SpaceOrder");
		int pages = 0;
		try {
			int num = 10;
			int n = q.list().size();

			if (n % num != 0) {
				pages = n / num + 1;
			}else {
				pages = n / num;
			}
		} finally {
			session.close();
		}
		return pages;
	}

	/**
	 * 是否通过空间申请，返回1通过
	 * @param id
	 * @return
	 */
	@Override
	public Integer isOrNadd(int id) {
//		Session session = this.getSession();
//		List<Object> objects= new ArrayList<Object>();
//		objects.add(permission);
//		objects.add(id);
//		org.hibernate.Query query = session.createQuery("update SpaceOrder set permission = ? where id=?").setParameter(0, objects);
//		int flag = 0;
//		try {
//			if (objects!=null&&objects.size()>0) {
//				for (int i = 0; i < objects.size(); i++) {
//					query.setParameter(i, objects.get(i));
//				}
//			}
//			flag = query.executeUpdate();
//		} finally {
//			session.close();
//		}
//		return flag;
		return 0;
	}

	/**
	 * 修改空间信息,成功返回1
	 * @param SpaceOrder
	 * @return
	 */
	@Override
	public Integer updateSpaceOrder(SpaceOrder SpaceOrder) {
//		Session session = this.getSession();
//		List<Object> objects= new ArrayList<Object>();
//		objects.add(SpaceOrder.getSpaceOrderName());
//		objects.add(SpaceOrder.getSpaceOrderSize());
//		objects.add(SpaceOrder.getUseSize());
//		objects.add(SpaceOrder.getStartTime());
//		objects.add(SpaceOrder.getExtraDays());
//		objects.add(SpaceOrder.getId());
		int flag = 0;
//		Query query = session.createQuery("update SpaceOrder set SpaceOrderName = ?,SpaceOrderSize=?,useSize=?,startTime=?,extraDays=? where id=?").setParameter(0, objects);
//		try {
//			if (objects!=null&&objects.size()>0) {
//				for (int i = 0; i < objects.size(); i++) {
//					query.setParameter(i, objects.get(i));
//				}
//			}
//			flag = query.executeUpdate();
//		} finally {
//			session.close();
//		}
		return flag;

	}

}
