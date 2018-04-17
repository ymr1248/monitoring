package com.monitoring.service;

import com.monitoring.entity.Operator;
import com.monitoring.entity.Permission;
import com.monitoring.entity.Video;
import com.monitoring.util.Pager;

import java.util.Date;
import java.util.List;

public interface OperatorService {

    public int loginOperator(String account,String password);

    public Operator getOperatorById(int id);

    public Operator getOperatorByAccount(String account);

    public boolean checkPhone(String phone);

    public int addOperator(Operator operator);

    public boolean deleteOperatorById(int id);

    public boolean update(Operator operator);

    public List<Operator> getOperators();

    public List<Operator> getOperators(int num);

    public List<Operator> getOperatorListByPermission(int permission, int num);

    public int getPageNumByPermission(int grade);

    public int getPageNum();

    public List<Operator> checkOperatorPsw(int id, String oldPassword);

    public boolean updateOperatorPsw(int id, String newPassword);

    public boolean updateOperatorInfo(int id, String name);

    public boolean updateOperatorPermission(int id, int permission);

    public Pager<Operator> getOperatorWithPage(int pageNum, int userId);

    public Pager<Video> getVideoByPageNumAndOperatorId(int pagaeNum,int operatorId);

    public Pager<Operator> getOperatorByPermission(int pageNum,Permission permission);

    public Pager<Operator> getOperatorByHigherPermissionId(int pageNum,int operatorId);

    public Pager<Operator> getOperatorByApplyState(int pageNum,int operatorId,int applyState);

    public boolean updateApplyState(int operatorId,int applyState);

    public boolean updatePermissionTime(int operatorId,Date permissionTime);

    public boolean agreePermissionTime(int operatorId);

    public boolean  resetApplyPermission(int operatorId,Date date);
}
