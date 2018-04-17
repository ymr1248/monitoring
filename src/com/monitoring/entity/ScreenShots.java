package com.monitoring.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "screenshots")
public class ScreenShots {
    @Id
    @Column(name = "shotsId",nullable = false,unique = true)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shotsId;
    //截图名称
    @Column(name = "shotsName",nullable = false,length = 32)
    private String shotsName;
    //截图保存地址
    @Column(name = "shotsURL",nullable = false,length = 32)
    private String shotsURL;
    //截图时间
    @Column(name = "shotsTime",nullable = false,length = 32)
    private Date shotsTime;
    //截图所属摄像头
    @ManyToOne(targetEntity = Monitoring.class)
    @JoinColumn(name = "monitorId")
    private Monitoring monitor;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private User user;

    public int getShotsId() {
        return shotsId;
    }

    public void setShotsId(int shotsId) {
        this.shotsId = shotsId;
    }

    public String getShotsName() {
        return shotsName;
    }

    public void setShotsName(String shotsName) {
        this.shotsName = shotsName;
    }

    public String getShotsURL() {
        return shotsURL;
    }

    public void setShotsURL(String shotsURL) {
        this.shotsURL = shotsURL;
    }

    public Date getShotsTime() {
        return shotsTime;
    }

    public void setShotsTime(Date shotsTime) {
        this.shotsTime = shotsTime;
    }

    public Monitoring getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitoring monitor) {
        this.monitor = monitor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ScreenShots{" +
                "shotsId=" + shotsId +
                ", shotsName='" + shotsName + '\'' +
                ", shotsURL='" + shotsURL + '\'' +
                ", shotsTime=" + shotsTime +
                ", monitor=" + monitor +
                ", user=" + user +
                '}';
    }
}
