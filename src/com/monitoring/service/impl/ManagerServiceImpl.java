package com.monitoring.service.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.monitoring.dao.ManagerDao;
import com.monitoring.entity.Manager;
import com.monitoring.service.ManagerService;
import com.monitoring.util.CyptoUtils;


@Service
public class ManagerServiceImpl implements ManagerService{
	private String APP_SECRET_KEY="sv1ey5gp6NeUEyC2GlovUw==";
    @Autowired
    ManagerDao managerDao;

    @Override
    public Manager loginManager(String account) {
        return managerDao.loginManager(account);
    }

    @Override
    public Manager getManagerByAccount(String account) {
        return managerDao.getManagerByAccount(account);
    }

    @Override
    public int managerLogin(String account, String password) {
        Manager manager = managerDao.loginManager(account);
        if (manager != null) {
            //账号正确
            if (manager.getPassword().equals(password)) {
                return 1;
            }else {
                return 0;
            }
        }else {
            //账号错误
            return -1;
        }
    }

    @Override
    public Manager getManagerById(int managerId) {
        return managerDao.getManagerById(managerId);
    }

    @Override
    public HashMap<String, String> getManagerInfo(int managerId) {
        Manager manager = getManagerById(managerId);
        HashMap<String,String> managerInfo = new HashMap<String, String>();
        managerInfo.put("managerId",String.valueOf(manager.getId()));
        managerInfo.put("managerName",manager.getManagerName());
        managerInfo.put("managerAccount",manager.getManagerAccount());
//        Constants.useOfficial();
//        Sender sender=new Sender(APP_SECRET_KEY);
//        Message message=new Message.Builder().title("")
//        		.description("")
//        		.restrictedPackageName("")
//        		.notifyType(1)
//        		.build();
//        Result result=sender.send(message, regId,3);
        
        return managerInfo;
    }

    @Override
    public ModelMap getManagerInfoByRequest(HttpServletRequest request, ModelMap modelMap) {
        int managerId = 0;
        try {
            managerId = Integer.parseInt(CyptoUtils.decode(request.getParameter("managerId")));
            HashMap<String,String> managerInfo = getManagerInfo(managerId);
            modelMap.addAttribute("managerInfo",managerInfo);
        } catch (NumberFormatException e) {
            modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
        }
        return modelMap;
    }
}
