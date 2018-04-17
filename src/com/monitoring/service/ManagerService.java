package com.monitoring.service;

import com.monitoring.entity.Manager;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface ManagerService {
    public Manager loginManager(String account);

    public Manager getManagerByAccount(String account);

    public int managerLogin(String account,String password);

    public Manager getManagerById(int managerId);

    public HashMap<String,String> getManagerInfo(int managerId);

    public ModelMap getManagerInfoByRequest(HttpServletRequest request,ModelMap modelMap);
}
