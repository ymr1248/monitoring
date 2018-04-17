package com.monitoring.dao;

import com.monitoring.entity.Manager;

public interface ManagerDao {
    public Manager loginManager(String account);
    public Manager getManagerByAccount(String account);
    public Manager getManagerById(int managerId);
}
