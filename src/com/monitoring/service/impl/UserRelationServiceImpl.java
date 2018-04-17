package com.monitoring.service.impl;

import com.monitoring.dao.UserRelationDao;
import com.monitoring.entity.UserRelation;
import com.monitoring.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wnf on 2017-12-28.
 */
@Service
public class UserRelationServiceImpl implements UserRelationService{
    @Autowired
    UserRelationDao userRelationDao;

    @Override
    public boolean addUserRelation(UserRelation userRelation) {
        if (userRelationDao.save(userRelation)==1) {
            return true;
        }
        return false;
    }

    @Override
    public List<UserRelation> getUserRelationList() {
        return userRelationDao.getUserRelationList();
    }

    /**
     * 通过上级id获取下一级用户
     * @param fatherId
     * @return
     */
    @Override
    public List<UserRelation> getUserRelationByFatherId(int fatherId) {
        return userRelationDao.getUserRelationByFatherId(fatherId);
    }

    @Override
    public UserRelation getUserRelationBySonId(int sonId) {
        return userRelationDao.getUserRelationBySonId(sonId);
    }

    @Override
    public boolean deleteUserRelationById(int id) {
        if (userRelationDao.deleteUserRelationById(id)==1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUserRelationBySonId(int id) {
        if (userRelationDao.deleteUserRelationBySonId(id)==1) {
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteUserRelationByFatherId(int id) {
        if (userRelationDao.deleteUserRelationByFatherId(id)==1) {
            return true;
        }
        return false;
    }
    @Override
    public List<UserRelation> getUserRelationListByFatherId(int fatherId) {
        return userRelationDao.getUserRelationListByFatherId(fatherId);
    }
    @Override
    public List<UserRelation> getUserRelationListByFatherId(int fatherId, int num) {
        return userRelationDao.getUserRelationListByFatherId(fatherId,num);
    }

    @Override
    public int getPagesByFatherId(int fatherId) {
        return userRelationDao.getPagesByfatherId(fatherId);
    }
}
