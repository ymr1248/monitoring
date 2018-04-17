package com.monitoring.service.impl;

import com.monitoring.dao.OperatorDao;
import com.monitoring.entity.*;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.OperatorService;

import com.monitoring.service.UserRelationService;
import com.monitoring.service.VideoService;
import com.monitoring.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    OperatorService operatorService;
    @Autowired
    OperatorDao operatorDao;
    @Autowired
    UserRelationService userRelationService;
    @Autowired
    MonitoringService monitoringService;
    @Autowired
    VideoService videoService;

    @Override
    public int loginOperator(String account, String password) {
        Operator operator = operatorDao.loginOperator(account);
        if (operator != null) {
            //the account is right
            if (operator.getPassword().equals(password)) {
                return 1;
            } else
                return 0;
        }
        return -1;
    }

    @Override
    public Operator getOperatorById(int id) {
        return operatorDao.getOperatorById(id);
    }

    @Override
    public Operator getOperatorByAccount(String account) {
        return operatorDao.getOperatorByAccount(account);
    }

    @Override
    public boolean checkPhone(String phone) {
        return operatorDao.checkPhone(phone);
    }

    @Override
    public int addOperator(Operator operator) {
        return operatorDao.addOperator(operator);
    }

    @Override
    public boolean deleteOperatorById(int id) {
        if (operatorDao.deleteOperatorById(id) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Operator operator) {
        if (operatorDao.update(operator) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Operator> getOperators() {
        return operatorDao.getOperators();
    }

    @Override
    public List<Operator> getOperators(int num) {
        return operatorDao.getOperators(num);
    }

    @Override
    public List<Operator> getOperatorListByPermission(int permission, int num) {
        return operatorDao.getOperatorListByPermission(permission, num);
    }

    @Override
    public int getPageNumByPermission(int grade) {
        return operatorDao.getPageNumByPermission(grade);
    }

    @Override
    public int getPageNum() {
        return operatorDao.getPageNum();
    }

    @Override
    public List<Operator> checkOperatorPsw(int id, String oldPassword) {
        return operatorDao.checkOperatorPsw(id, oldPassword);
    }

    @Override
    public boolean updateOperatorPsw(int id, String newPassword) {
        if (operatorDao.updateOperatorPsw(id, newPassword) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOperatorInfo(int id, String name) {
        if (operatorDao.updateOperatorInfo(id, name) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOperatorPermission(int id, int permission) {
        if (operatorDao.updateOperatorPermission(id, permission) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Pager<Operator> getOperatorWithPage(int pageNum, int userId) {

        List<UserRelation> list = userRelationService.getUserRelationListByFatherId(userId);
        List<Operator> operators = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            operators.add(list.get(i).getOperator());
        }
        Pager<Operator> pager = new Pager<>(pageNum, 10, operators);
        return pager;
    }

    @Override
    public Pager<Video> getVideoByPageNumAndOperatorId(int pagaeNum, int operatorId) {
        UserRelation userRelation = userRelationService.getUserRelationBySonId(operatorId);
        User user = userRelation.getUser();
        int userId = user.getId();
        List<Monitoring> monitorings = monitoringService.getMonitoringsByUserId(userId);
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < monitorings.size(); i++) {
            videos.addAll(videoService.getVideos(user, monitorings.get(i)));
        }
        Pager<Video> videoPager = new Pager<>(pagaeNum, 6, videos);
        return videoPager;
    }

    @Override
    public Pager<Operator> getOperatorByPermission(int pageNum, Permission permission) {
        List<Operator> operators = operatorDao.getOperatorByPermission(permission);
        Pager<Operator> pager = new Pager<>(pageNum, 10, operators);


        return pager;
    }

    @Override
    public Pager<Operator> getOperatorByHigherPermissionId(int pageNum, int operatorId) {
        User user = userRelationService.getUserRelationBySonId(operatorId).getUser();
        List<UserRelation> userRelations = userRelationService.getUserRelationByFatherId(user.getId());
        List<Operator> operators = new ArrayList<>();
        for (int i = 0; i < userRelations.size(); i++) {
            if (userRelations.get(i).getOperator().getOperatorId() != operatorId) {
                operators.add(userRelations.get(i).getOperator());
            }
        }
        List<Operator> conditionOperator = new ArrayList<>();
        for (int j = 0; j < operators.size(); j++) {
            if (operators.get(j).getPermission().ordinal() >= operatorDao.getOperatorById(operatorId).getPermission().ordinal()) {
                conditionOperator.add(operators.get(j));
            }
        }
        Pager<Operator> pager1 = null;
        try {
            pager1 = new Pager<>(pageNum, 10, conditionOperator);
        } catch (Exception e) {
            System.out.println(e);

        }
        System.out.println(pager1.toString());
        return pager1;
    }

    @Override
    public Pager<Operator> getOperatorByApplyState(int pageNum, int operatorId, int applyState) {
        User user = userRelationService.getUserRelationBySonId(operatorId).getUser();
        List<UserRelation> userRelations = userRelationService.getUserRelationByFatherId(user.getId());
        List<Operator> operators = new ArrayList<>();
        for (int i = 0; i < userRelations.size(); i++) {
            if (userRelations.get(i).getOperator().getOperatorId() != operatorId) {
                operators.add(userRelations.get(i).getOperator());
            }
        }
        List<Operator> conditionOperator = new ArrayList<>();
        for (int j = 0; j < operators.size(); j++) {
            if (operators.get(j).getPermission().ordinal() >= operatorDao.getOperatorById(operatorId).getPermission().ordinal()) {
                conditionOperator.add(operators.get(j));
            }
        }
        List<Operator> operators1 = new ArrayList<>();
        for (int i = 0; i < conditionOperator.size(); i++) {
            if (conditionOperator.get(i).getApplyState() == operatorDao.getOperatorById(operatorId).getPermission().ordinal()) {
                operators1.add(conditionOperator.get(i));
            }
        }
        Pager<Operator> pager2 = new Pager<>(pageNum, 10, operators1);
        return pager2;

    }

    @Override
    public boolean updateApplyState(int operatorId, int applyState) {
        if (operatorDao.updateApplyState(operatorId, applyState) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePermissionTime(int operatorId, Date permissionTime) {
        if (operatorDao.updatePermissionTime(operatorId, permissionTime) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean agreePermissionTime(int operatorId) {
        Operator operator = operatorDao.getOperatorById(operatorId);
        int permissionOrdinal;
        Date date = new Date();
        date.setTime(date.getTime() + 86400000);
        if ((permissionOrdinal = operator.getApplyState() + 1) != -1) {
            if (operatorDao.updateApplyPermission(operatorId, Permission.valueOf("PERMISSION" + permissionOrdinal)) == 1) {
                if (operatorDao.updatePermissionTime(operatorId, date) == 1) {
                    if (operatorDao.updateApplyState(operatorId, -1) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean resetApplyPermission(int operatorId, Date date) {
        Operator operator = operatorDao.getOperatorById(operatorId);
        Date permissionTime = operator.getPermissionTime();
        if (permissionTime != null) {
            if (date.after(permissionTime)) {
                if (operatorDao.updatePermissionTime(operatorId, null) == 1) {
                    if (operatorDao.updateApplyPermission(operatorId, null) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
