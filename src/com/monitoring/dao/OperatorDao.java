package com.monitoring.dao;

import com.monitoring.entity.Operator;
import com.monitoring.entity.Permission;

import java.util.Date;
import java.util.List;

public interface OperatorDao {

    public Operator loginOperator(String account);

    public Operator getOperatorById(int id);

    public Operator getOperatorByAccount(String account);

    public boolean checkPhone(String phone);

    public int addOperator(Operator operator);

    public Integer deleteOperatorById(int id);

    public int update(Operator operator);

    public List<Operator> getOperators();

    public List<Operator> getOperators(int num);

    public List<Operator> getOperatorListByPermission(int permission, int num);

    public int getPageNumByPermission(int grade);

    public int getPageNum();

    public List<Operator> checkOperatorPsw(int id, String oldPassword);

    public Integer updateOperatorPsw(int id, String newPassword);

    public Integer updateOperatorInfo(int id, String name);

    public Integer updateOperatorPermission(int id, int permission);

    public List<Operator> getOperatorByPermission(Permission permission);

    public List<Operator> getOperatorByApplyState(int applyState);

    public int updateApplyState(int operatorId,int applyState);

    public int updatePermissionTime(int operatorId,Date permissionTime);

    public int updateApplyPermission(int operatorId,Permission applyPermission);
}
