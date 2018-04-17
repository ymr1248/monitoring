package com.monitoring.dao.impl;

import com.monitoring.dao.ScreenShotsDao;
import com.monitoring.entity.ScreenShots;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ScreenShotsDaoImpl implements ScreenShotsDao {
    @Resource
    SessionFactory sessionFactory;
    public Session getSession(){
        return sessionFactory.openSession();
    }

    @Override
    public ScreenShots getScreenShotsById(int shotsId) {
        Session session = this.getSession();
        ScreenShots screenShots = new ScreenShots();
        try {
            screenShots = (ScreenShots) session.createQuery("from ScreenShots where shotsId = ?").setParameter(0, shotsId).uniqueResult();
        } finally {
            session.close();
        }
        return screenShots;
    }

    @Override
    public ScreenShots getScreenShotsByMonitorId(int monitorId) {
        Session session = this.getSession();
        ScreenShots screenShots = new ScreenShots();
        try {
            screenShots = (ScreenShots) session.createQuery("from ScreenShots where monitorId = ?").setParameter(0, monitorId).uniqueResult();
        } finally {
            session.close();
        }
        return screenShots;
    }

    @Override
    public ScreenShots getScreenShotsByName(String name) {
        Session session = this.getSession();
        ScreenShots screenShots = new ScreenShots();
        try {
            screenShots = (ScreenShots) session.createQuery("from ScreenShots where shotsName = ?").setParameter(0, name).uniqueResult();
        } finally {
            session.close();
        }
        return screenShots;
    }

    @Override
    public int addScreenShots(ScreenShots screenShots) {
        Session session = this.getSession();
        try {
            session.save(screenShots);
        } finally {
            session.close();
        }
        return 1;
    }

    @Override
    public int deleteScreenShotsById(int shotsId) {
        Session session = this.getSession();
        int result = 0;
        try {
            result = session.createQuery("delete from ScreenShots where shotsId = ?").setParameter(0, shotsId).executeUpdate();
        } finally {
            session.close();
        }
        return result;
    }
    
    @Override
    public int deleteScreenShotsByUserId(int userId) {
        Session session = this.getSession();
        int result = 0;
        try {
            result = session.createQuery("delete from ScreenShots where userId = ?").setParameter(0, userId).executeUpdate();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveOrupdate(ScreenShots screenShots) {
        Session session = this.getSession();
        try {
            session.saveOrUpdate(screenShots);
        } finally {
            session.close();
        }
    }

    @Override
    public List<ScreenShots> getScreenShots() {
        Session session = this.getSession();
        List<ScreenShots> list =  new ArrayList<ScreenShots>();
        try {
            list =  session.createCriteria(ScreenShots.class).list();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List<ScreenShots> getScreenShotsPageList(int pageNum) {
        Session session = this.getSession();
        Query q = session.createQuery("from ScreenShots");
        List<ScreenShots> screenShotsList =  new ArrayList<ScreenShots>();
        try {
            int num = 10;
            int begin = (pageNum - 1) * num;
            try {
                screenShotsList= q.setFirstResult(begin).setMaxResults(num).list();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } finally {
            session.close();
        }
        return screenShotsList;
    }

    @Override
    public int pageS() {
        Session session = this.getSession();
        Query q = session.createQuery("from ScreenShots");
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


    @Override
    public int updateScreenShots(ScreenShots screenShots) {
        Session session = this.getSession();
        List<Object> objects= new ArrayList<Object>();
        objects.add(screenShots.getShotsName());
        objects.add(screenShots.getShotsURL());
        objects.add(screenShots.getShotsId());
        int flag = 0;
        org.hibernate.Query query = session.createQuery("update ScreenShots set shotsName = ?,shotsURL=? where shotsId=?").setParameter(0, objects);
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

    @Override
    public List<ScreenShots> getUserScreenShotsByMonitor(int monitorId) {
        Session session = this.getSession();
        Query q = session.createQuery("from ScreenShots where monitorId = ?").setParameter(0,monitorId);
        session.close();
        return q.list();
    }

    @Override
    public List<ScreenShots> getScreenShotsByUser(int userId) {
        List<ScreenShots> list = new ArrayList<>();
        Session session = this.getSession();
        try{
            Query q = session.createQuery("from ScreenShots where userId = ?").setParameter(0,userId);
            list = q.list();
        }finally {
            session.close();
        }

        return list;
    }
}
