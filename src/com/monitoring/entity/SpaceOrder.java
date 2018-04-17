package com.monitoring.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by wnf on 2018-01-22.
 */
@Entity
@Table(name="spaceorder")
public class SpaceOrder {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //充值大小
    @Column(name = "extraSize", nullable = false, length = 32)
    private String extraSize;

    //充值空间  开始时间
    @Column(name = "startTime",nullable = false, length = 32)
    private String startTime;
    //充值月数
    @Column(name = "extraMoths",nullable = false)
    private int extraMoths;
    //空间ID
    @ManyToOne(targetEntity = Space.class)
    @JoinColumn(name = "spaceId")
    private Space space;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExtraSize() {
        return extraSize;
    }

    public void setExtraSize(String extraSize) {
        this.extraSize = extraSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getExtraMoths() {
        return extraMoths;
    }

    public void setExtraMoths(int extraMoths) {
        this.extraMoths = extraMoths;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    @Override
    public String toString() {
        return "SpaceOrder{" +
                "id=" + id +
                ", extraSize='" + extraSize + '\'' +
                ", startTime='" + startTime + '\'' +
                ", extraMoths=" + extraMoths +
                ", space=" + space +
                '}';
    }
}
