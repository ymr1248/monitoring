package com.monitoring.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "operator")
public class Operator{
    @Id
    @Column(name = "operatorId", nullable = false, unique = true)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operatorId;
    // 登录账号
    @Column(name = "operatorAccount", nullable = false, length = 16, unique = true)
    private String operatorAccount;
    //操作员地址
    @Column(name = "operatorAddress",nullable = true, length = 32)
    private String operatorAddress;
    //操作员电话
    @Column(name = "operatorPhone",nullable = false,length = 16,unique = true)
    private String operatorPhone;
    //操作员密码
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    //操作员用户名
    @Column(name = "operatorName", nullable = false, length = 16)
    private String operatorName;
    //操作员权限等级1,2,3,4,5,决定操作员的使用权限
    @Column(name = "permission",nullable = false)
    private Permission permission;
    //操作员申请的权限等级
    @Column(name = "applyPermission",nullable = true)
    private Permission applyPermission;
    //申请等级状态，默认为-1，为-1的时候不申请0为申请最高权限，1为次高级以此类推
    @Column(name = "applyState",nullable = true)
    private int applyState;
    @Column(name = "permissionTime",nullable = true,length = 64)
    private Date permissionTime;

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorAccount() {
        return operatorAccount;
    }

    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount;
    }

    public String getOperatorAddress() {
        return operatorAddress;
    }

    public void setOperatorAddress(String operatorAddress) {
        this.operatorAddress = operatorAddress;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Permission getApplyPermission() {
        return applyPermission;
    }

    public void setApplyPermission(Permission applyPermission) {
        this.applyPermission = applyPermission;
    }

    public int getApplyState() {
        return applyState;
    }

    public void setApplyState(int applyState) {
        this.applyState = applyState;
    }

    public Date getPermissionTime() {
        return permissionTime;
    }

    public void setPermissionTime(Date permissionTime) {
        this.permissionTime = permissionTime;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "operatorId=" + operatorId +
                ", operatorAccount='" + operatorAccount + '\'' +
                ", operatorAddress='" + operatorAddress + '\'' +
                ", operatorPhone='" + operatorPhone + '\'' +
                ", password='" + password + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", permission=" + permission +
                ", applyPermission=" + applyPermission +
                ", applyState=" + applyState +
                ", permissionTime=" + permissionTime +
                '}';
    }
}
