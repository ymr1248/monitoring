package com.monitoring.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // 登录账号
    @Column(name = "managerAccount", nullable = false, length = 16, unique = true)
    private String managerAccount;
    //管理员地址
    @Column(name = "managerAddress",nullable = true, length = 32)
    private String managerAddress;
    //管理员电话
    @Column(name = "managerPhone",nullable = false,length = 16,unique = true)
    private String managerPhone;
    //管理员e-mail
    @Column(name = "managerEMail",nullable = true,length = 32)
    private String managerEMail;
    //管理员头像地址
    @Column(name = "avatar",nullable = true,length = 50)
    private String avatar;
    //管理员密码
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    //管理员用户名
    @Column(name = "managerName", nullable = false, length = 16)
    private String managerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManagerAccount() {
        return managerAccount;
    }

    public void setManagerAccount(String managerAccount) {
        this.managerAccount = managerAccount;
    }

    public String getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerEMail() {
        return managerEMail;
    }

    public void setManagerEMail(String managerEMail) {
        this.managerEMail = managerEMail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", managerAccount='" + managerAccount + '\'' +
                ", managerAddress='" + managerAddress + '\'' +
                ", managerPhone='" + managerPhone + '\'' +
                ", managerEMail='" + managerEMail + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", managerName='" + managerName + '\'' +
                '}';
    }
}
