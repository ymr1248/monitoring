package com.monitoring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.monitoring.dao.SpaceDao;
import com.monitoring.entity.Space;
/**
 * @author mry
 *2018年1月2日 上午11:23:02
 */
@Repository
public class SpaceDaoImpl implements SpaceDao{
	@Resource
	SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.openSession();
	}

	@Override
	public Space getSpaceById(int id) {
		Session session = this.getSession();
		Space space = new Space();
		try {
			space = (Space) session.createQuery("from Space where id = ?").setParameter(0, id).uniqueResult();
		} finally {
			session.close();
		}
		return space;
	}

	@Override
	public Space getSpaceByUserId(int userId) {
		Session session = this.getSession();
		Space space = new Space();
		try {
			space = (Space) session.createQuery("from Space where userId = ?").setParameter(0, userId).uniqueResult();
		} finally {
			session.close();
		}
		return space;
	}

	@Override
	public Space getSpaceByName(String name) {
		Session session = this.getSession();
		Space space = new Space();
		try {
			space = (Space) session.createQuery("from Space where spaceName = ?").setParameter(0, name).uniqueResult();
		} finally {
			session.close();
		}
		return space;
	}
	
	@Override
	public List<Space> getSpaceByNameLike(String name) {
		Session session = this.getSession();
		List<Space> list = new ArrayList<Space>();
		try {
			list =  session.createQuery("from Space where spaceName like ?").setParameter(0, "%"+name+"%").list();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public void addSpace(Space space) {
		Session session = this.getSession();
		try {
			session.save(space);
		} finally {
			session.close();
		}
	}

	@Override
	public int deleteSpaceById(int id) {
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete from Space where id = ?").setParameter(0, id).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public int deleteSpaceByUserId(int userId) {
		Session session = this.getSession();
		int result = 0;
		try {
			result = session.createQuery("delete from Space where userId = ?").setParameter(0, userId).executeUpdate();
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public void saveOrupdate(Space space) {
		Session session = this.getSession();
		try {
			session.saveOrUpdate(space);
		} finally {
			session.close();
		}
	}

	@Override
	public List<Space> getSpaces() {
		Session session = this.getSession();
		List<Space> list =  new ArrayList<Space>();
		try {
			list =  session.createCriteria(Space.class).list();
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
	public List<Space> getSpacesPageList(int pageNum) {
		Session session = this.getSession();
		Query q = session.createQuery("from Space");
		List<Space> spaceList =  new ArrayList<Space>();
		try {
			int num = 10;
			int begin = (pageNum - 1) * num;
			try {
				spaceList= q.setFirstResult(begin).setMaxResults(num).list();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			session.close();
		}
		return spaceList;
	}

	/**
	 * 获得页数
	 * @return
	 */
	@Override
	public int pageS() {
		Session session = this.getSession();
		Query q = session.createQuery("from Space");
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
//		org.hibernate.Query query = session.createQuery("update Space set permission = ? where id=?").setParameter(0, objects);
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
	 * @param space
	 * @return
	 */
	@Override
	public Integer updateSpace(Space space) {
		Session session = this.getSession();
		List<Object> objects= new ArrayList<Object>();
		objects.add(space.getSpaceName());
		objects.add(space.getSpaceSize());
		objects.add(space.getUseSize());
		objects.add(space.getId());
		int flag = 0;
		org.hibernate.Query query = session.createQuery("update Space set spaceName = ?,spaceSize=?,useSize=? where id=?").setParameter(0, objects);
		try {
			if (objects!=null&&objects.size()>0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			flag = query.executeUpdate();
		} finally {
			session.close();
		}
		return flag;

	}

	/**
	 * 修改充值空间的大小
	 * @param extraSize
	 * @param id
	 * @return
	 */
	@Override
	public Integer updateSpaceExtraSize(String extraSize,int id) {
		Session session = this.getSession();
		List<Object> objects= new ArrayList<Object>();
		objects.add(extraSize);
		objects.add(id);
		int flag = 0;
		org.hibernate.Query query = session.createQuery("update Space set extraSize=? where id=?").setParameter(0, objects);
		try {
			if (objects!=null&&objects.size()>0) {
				for (int i = 0; i < objects.size(); i++) {
					query.setParameter(i, objects.get(i));
				}
			}
			flag = query.executeUpdate();
		} finally {
			session.close();
		}
		return flag;

	}

}
