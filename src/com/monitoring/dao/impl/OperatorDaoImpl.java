package com.monitoring.dao.impl;

import com.monitoring.dao.OperatorDao;
import com.monitoring.entity.Operator;
import com.monitoring.entity.Permission;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class OperatorDaoImpl implements OperatorDao {

    /* (non-Javadoc)
     * @see com.yongxin.dao.UserDao#getOperatorById(int)
     */
    @Resource
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Operator loginOperator(String account) {
        Session session = this.getSession();
        Operator operator = new Operator();
        try {
            operator = (Operator) session.createQuery("from Operator where operatorAccount=?").setParameter(0, account).uniqueResult();
        } finally {
            session.close();
        }
        return operator;

    }

    @Override
    public Operator getOperatorById(int id) {
        Session session = this.getSession();
        Operator operator = new Operator();
        try {
            operator = (Operator) session.createQuery("from Operator where operatorId=?").setParameter(0, id).uniqueResult();
        } finally {
            session.close();
        }
        return operator;

    }

    @Override
    public Operator getOperatorByAccount(String account) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        Operator operator = new Operator();
        try {
            operator = (Operator) session.createQuery("from Operator where operatorAccount=?").setParameter(0, account).uniqueResult();
        } finally {
            session.close();
        }
        return operator;
    }


    @Override
    public int addOperator(Operator operator) {
        Session session = this.getSession();
        try {
            session.saveOrUpdate(operator);
        } finally {
            session.close();
        }
        return 1;
    }


    @Override
    public Integer deleteOperatorById(int id) {
        Session session = this.getSession();
        int result = 0;
        try {
            result = session.createQuery("delete Operator where operatorId=?").setParameter(0, id).executeUpdate();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public int update(Operator operator) {
        Session session = this.getSession();
        Integer result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            String hql;

            objects.add(operator.getOperatorName());
            objects.add(operator.getOperatorAddress());
            objects.add(operator.getPermission());
            objects.add(operator.getOperatorPhone());
            objects.add(operator.getPassword());

            hql = "update Operator set operatorName = ?,operatorAddress=?,permission=? ,operatorPhone=?,password=? where operatorId=?";

            objects.add(operator.getOperatorId());
            org.hibernate.Query query = session.createQuery(hql)
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }
        return result;

    }


    @Override
    public List<Operator> getOperators() {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        List<Operator> operatorList = new ArrayList<>();
        try {
            operatorList = session.createCriteria(Operator.class).list();
        } finally {
            session.close();
        }
        return operatorList;
    }


    @Override
    public List<Operator> getOperators(int num) {
//		 * 分页查询步骤
        Session session = this.getSession();
        List<Operator> operatorList = new ArrayList<>();
        try {
            Criteria query = session.createCriteria(Operator.class);
            ScrollableResults scroll = query.scroll();
            // 滚动到最后一行
            scroll.last();
            // 一共多少条数据
            int n = scroll.getRowNumber() + 1;
            System.out.println(n);
            // 设定一页显示多少条
            int nu = 10;
            int begin = (num - 1) * nu;
            //int last = n - nu * num;
            // 设置分页位置
            query.setFirstResult(begin);
            //结束位置
            query.setMaxResults(nu);
            operatorList = query.list();
            System.out.println(operatorList);
        } finally {
            session.close();
        }
        return operatorList;
    }

    @Override
    public int getPageNum() {
        // TODO Auto-generated method stub
        Session session = this.getSession();

        int pages = 0;
        try {
            Criteria query = session.createCriteria(Operator.class);
            ScrollableResults scroll = query.scroll();
            // 滚动到最后一行
            scroll.last();
            // 一共多少条数据
            int n = scroll.getRowNumber() + 1;
            System.out.println(n);
            int num = 10;
            if (n % num != 0) {
                pages = n / num + 1;
            } else {
                pages = n / num;
            }
        } finally {
            session.close();
        }
        return pages;
    }

    @Override
    public List<Operator> checkOperatorPsw(int id, String oldPassword) {
        Session session = this.getSession();
        List<Operator> operatorList = new ArrayList<>();
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(id);
            objects.add(oldPassword);
            Query q = session.createQuery("from Operator where operatorId=? and password=?");
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    q.setParameter(i, objects.get(i));
                }
            }
            operatorList = q.list();
        } finally {
            session.close();
        }
        return operatorList;
    }

    @Override
    public Integer updateOperatorPsw(int id, String newPassword) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(newPassword);
            objects.add(id);
            org.hibernate.Query query = session.createQuery("update Operator set password=? where operatorId=?")
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public Integer updateOperatorInfo(int id, String name) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(name);
            objects.add(id);
            org.hibernate.Query query = session.createQuery("update Operator set operatorName=? where operatorId=?")
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * 分页查询
     *
     * @param permission
     * @param num
     * @return
     */
    @Override
    public List<Operator> getOperatorListByPermission(int permission, int num) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        List<Operator> operatorList = new ArrayList<>();
        try {
            /*
             * 分页查询步骤
             */
//			OperatorGrade operatorGrade = (OperatorGrade) session.createQuery("from OperatorGrade where OperatorGrade=?").setParameter(0, grade).uniqueResult();
            List<Object> param = new ArrayList<Object>();
            param.add(permission);
            org.hibernate.Query q = session.createQuery("from Operator where permission = ?");
            if (param != null && param.size() > 0) {
                for (int j = 0; j < param.size(); j++) {
                    q.setParameter(j, param.get(j));
                }
            }
            int nu = 10;
            int begin = (num - 1) * nu;
            operatorList = q.setFirstResult(begin).setMaxResults(nu).list();
        } finally {
            session.close();
        }
        return operatorList;
    }

    /**
     * 获取页数
     *
     * @param permission
     * @return
     */
    @Override
    public int getPageNumByPermission(int permission) {
        Session session = this.getSession();
        List<Object> param = new ArrayList<Object>();
        param.add(permission);
        org.hibernate.Query q = session.createQuery("from Operator where permission = ?");
        int pages = 0;
        try {
            if (param != null && param.size() > 0) {
                for (int j = 0; j < param.size(); j++) {
                    q.setParameter(j, param.get(j));
                }
            }
            int num = 10;
            int n = q.list().size();

            if (n % num != 0) {
                pages = n / num + 1;
            } else {
                pages = n / num;
            }
        } finally {
            session.close();
        }
        return pages;

    }

    @Override
    public Integer updateOperatorPermission(int id, int permission) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(permission);
            objects.add(id);
            org.hibernate.Query query = session.createQuery("update Operator set permission=? where operatorId=?")
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public List<Operator> getOperatorByPermission(Permission permission) {
        Session session = this.getSession();
        List<Object> param = new ArrayList<Object>();
        List<Operator> operators = new ArrayList<>();
        param.add(permission);
        org.hibernate.Query q = session.createQuery("from Operator where permission = ?");
        try {
            if (param != null && param.size() > 0) {
                for (int i = 0; i < param.size(); i++) {
                    q.setParameter(i, param.get(i));
                }
            }
            operators = q.list();
        } finally {
            session.close();
        }

        return operators;
    }

    @Override
    public List<Operator> getOperatorByApplyState(int applyState) {
        Session session = this.getSession();
        List<Object> param = new ArrayList<Object>();
        List<Operator> operators = new ArrayList<>();
        param.add(applyState);
        org.hibernate.Query q = session.createQuery("from Operator where applyState = ?");
        try {
            if (param != null && param.size() > 0) {
                for (int i = 0; i < param.size(); i++) {
                    q.setParameter(i, param.get(i));
                }
            }
            operators = q.list();
        } finally {
            session.close();
        }

        return operators;
    }

    @Override
    public int updateApplyState(int operatorId,int applyState) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(applyState);
            objects.add(operatorId);
            org.hibernate.Query query = session.createQuery("update Operator set applyState=? where operatorId=?")
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public int updatePermissionTime(int operatorId, Date permissionTime) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(permissionTime);
            objects.add(operatorId);
            org.hibernate.Query query = session.createQuery("update Operator set permissionTime=? where operatorId=?")
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public int updateApplyPermission(int operatorId,Permission applyPermission) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(applyPermission);
            objects.add(operatorId);
            org.hibernate.Query query = session.createQuery("update Operator set applyPermission=? where operatorId=?")
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }

        return result;
    }


    /* (non-Javadoc)
     * @see com.monitoring.dao.OperatorDao#checkPhone(int)
     */
    @Override
    public boolean checkPhone(String phone) {
        Session session = this.getSession();
        Operator operator = new Operator();
        try {
            operator = (Operator) session.createQuery("from Operator where operatorPhone=?").setParameter(0, phone).uniqueResult();
        } finally {
            session.close();
        }
        if (operator == null) {
            return true;
        }
        return false;
    }
}
