package com.monitoring.service.impl;

import java.util.List;
import com.monitoring.entity.MonitorGroup;
import com.monitoring.service.MonitorgroupService;
import com.monitoring.service.UserGradeService;
import com.monitoring.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.entity.User;
import com.monitoring.service.UserService;
import com.monitoring.dao.UserDao;

/**
 * @author mry
 * 2018年1月2日 上午8:44:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    UserGradeService userGradeService;
    @Autowired
    MonitorgroupService monitorgroupService;

    @Override
    public int loginUser(String account, String password) {
        User user = userDao.loginUser(account);
        if (user != null) {
            //the account is right
            if (user.getUserGrade().getId() == 1) {
                return 2;
            } else {
                if (user.getPassword().equals(password)) {
                    return 1;
                } else
                    return 0;
            }
        }
        return -1;
    }

    @Override
    public User getUserById(int id) {
        // TODO Auto-generated method stub
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByAccount(String account) {
        return userDao.getUserByAccount(account);
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#getUserByName(java.lang.String)
     */
    @Override
    public User getUserByName(String name) {
        // TODO Auto-generated method stub
        return userDao.getUserByName(name);
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#addUser(com.yongxin.entity.User)
     */
    @Override
    public int addUser(User user) {
        // TODO Auto-generated method stub
        return userDao.addUser(user);
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#deleteUserById(int)
     */
    @Override
    public boolean deleteUserById(int id) {
        // TODO Auto-generated method stub
        if (userDao.deleteUserById(id) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserPermission(int id, int permission) {
        if (userDao.updateUserPermission(id, permission) == 1) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#getUsers()
     */
    @Override
    public List<User> getUsers() {
        // TODO Auto-generated method stub
        return userDao.getUsers();
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#getUsers(int)
     */
    @Override
    public List<User> getUsers(int num) {
        // TODO Auto-generated method stub
        return userDao.getUsers(num);
    }

    @Override
    public Pager<User> getUserByUserGradeId(int pageNum, int userGradeId) {
        List<User> users = userDao.getUserByUserGradeId(userGradeId);
        Pager<User> pager = new Pager<>(pageNum,10,users);
        return pager;
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#getPageNum()
     */
    @Override
    public int getPageNum() {
        // TODO Auto-generated method stub
        return userDao.getPageNum();
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#checkUserPsw(int, java.lang.String)
     */
    @Override
    public boolean checkUserPsw(int id, String oldPassword) {
        // TODO Auto-generated method stub
        List<User> managers = userDao.checkUserPsw(id, oldPassword);
        if (managers != null && managers.size() > 0) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#updateUserPsw(int, java.lang.String)
     */
    @Override
    public boolean updateUserPsw(int id, String newPassword) {
        // TODO Auto-generated method stub
        if (userDao.updateUserPsw(id, newPassword) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserInfo(int id, String name) {
        // TODO Auto-generated method stub
        if (userDao.updateUserInfo(id, name) == 1) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.yongxin.service.UserService#update(com.yongxin.entity.User)
     */
    @Override
    public boolean update(User user) {
        // TODO Auto-generated method stub
        if (userDao.update(user) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> getUserListByPermission(int permission, int num) {
        return userDao.getUserListByPermission(permission, num);
    }

    @Override
    public int getPageNumByPermission(int grade) {
        return userDao.getPageNumByPermission(grade);
    }

    @Override
    public boolean isOrNotAgreeAdd(int id) {
        if (userDao.isOrNotAgreeAdd(id) == 1) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.monitoring.service.UserService#updateUserGrade(int, int)
     */
    @Override
    public boolean updateUserGrade(int id, int userGradeId) {
        // TODO Auto-generated method stub
        if (userDao.updateUserGrade(id, userGradeId) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int isOrNotAdded(String avatar, String userName, String userAccount, String password, String userAddress, String userPhone, String userEmail, int userGradeId) {
        if (getUserByAccount(userAccount) != null) {
            return -1;
        } else if (!(checkPhone(userPhone))) {
            return 0;
        } else {
            User user = new User();
            user.setUserAddress(userAddress);
            user.setUserGrade(userGradeService.getUserGradeById(userGradeId));
            user.setUserName(userName);
            user.setPassword(password);
            user.setUserAccount(userAccount);
            user.setUserPhone(userPhone);
            user.setAvatar(avatar);
            user.setUserEMail(userEmail);
            addUser(user);
            MonitorGroup monitorGroup = new MonitorGroup();
            monitorGroup.setUser(user);
            monitorGroup.setGroupName("默认组");
            monitorGroup.setGroupType("默认类型");
            monitorgroupService.add(monitorGroup);
            return 1;
        }
    }

    @Override
    public boolean updateUserAppId(int userId, String appId) {
        if (userDao.updateUserAppId(userId,appId) == 1){
            return true;
        }
        return false;
    }


    @Override
    public boolean checkPhone(String phone) {
        // TODO Auto-generated method stub
        return userDao.checkPhone(phone);
    }

}
