package com.monitoring.dao.impl;

import com.monitoring.dao.ManagerDao;
import com.monitoring.entity.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ManagerDaoImpl implements ManagerDao{
    @Resource
    private SessionFactory sessionFactory;
    private Session getSession() {
        return sessionFactory.openSession();
    }


    @Override
    public Manager loginManager(String account) {
        Session session = this.getSession();
        Manager manager = new Manager();
        try {
            manager = (Manager) session.createQuery("from Manager where managerAccount=?").setParameter(0, account).uniqueResult();
        } finally {
            session.close();
        }
        return manager;
    }

    @Override
    public Manager getManagerByAccount(String account) {
        Session session = this.getSession();
        Manager manager = new Manager();
        try {
            manager = (Manager) session.createQuery("from Manager where managerAccount=?").setParameter(0, account).uniqueResult();
        } finally {
            session.close();
        }
        return manager;
    }

    @Override
    public Manager getManagerById(int managerId) {
        Session session = this.getSession();
        Manager manager = new Manager();
        try {
            manager = (Manager) session.createQuery("from Manager where id=?").setParameter(0, managerId).uniqueResult();
        } finally {
            session.close();
        }
        return manager;
    }
}
