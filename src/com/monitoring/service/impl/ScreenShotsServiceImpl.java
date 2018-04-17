package com.monitoring.service.impl;

import com.monitoring.dao.ScreenShotsDao;
import com.monitoring.entity.ScreenShots;
import com.monitoring.service.MonitoringService;
import com.monitoring.service.ScreenShotsService;
import com.monitoring.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ScreenShotsServiceImpl implements ScreenShotsService{
    @Autowired
    ScreenShotsDao screenShotsDao;
    @Autowired
    MonitoringService monitoringService;

    @Override
    public ScreenShots getScreenShotsById(int shotsId) {
        return screenShotsDao.getScreenShotsById(shotsId);
    }

    @Override
    public ScreenShots getScreenShotsByMonitorId(int monitorId) {
        return screenShotsDao.getScreenShotsByMonitorId(monitorId);
    }

    @Override
    public ScreenShots getScreenShotsByName(String name) {
        return screenShotsDao.getScreenShotsByName(name);
    }

    @Override
    public boolean addScreenShots(ScreenShots screenShots) {
        if(screenShotsDao.addScreenShots(screenShots) == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteScreenShotsById(int shotsId) {
        if (screenShotsDao.deleteScreenShotsById(shotsId) == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteScreenShotsByUserId(int userId) {
        if (screenShotsDao.deleteScreenShotsByUserId(userId) == 1){
            return true;
        }
        return false;
    }
    
    @Override
    public void saveOrupdate(ScreenShots screenShots) {
        screenShotsDao.saveOrupdate(screenShots);
    }

    @Override
    public List<ScreenShots> getScreenShots() {
        return screenShotsDao.getScreenShots();
    }

    @Override
    public List<ScreenShots> getScreenShotsPageList(int pageNum) {
        return screenShotsDao.getScreenShotsPageList(pageNum);
    }

    @Override
    public int pageS() {
        return screenShotsDao.pageS();
    }

    @Override
    public boolean updateScreenShots(ScreenShots screenShots) {
        if(screenShotsDao.updateScreenShots(screenShots) == 1){
            return true;
        }
        return false;
    }

    @Override
    public Pager<ScreenShots> getUserScreenShots(int pageNum,int monitorId) {
        List<ScreenShots> screenShots = screenShotsDao.getUserScreenShotsByMonitor(monitorId);
        Pager<ScreenShots> pager = new Pager<>(pageNum,10,screenShots);
        return pager;
    }

    @Override
    public List<ScreenShots> getUserScreenShots(int monitorId) {
        return screenShotsDao.getUserScreenShotsByMonitor(monitorId);
    }

    @Override
    public Pager<ScreenShots> getScreenShotsByUser(int pageNum, int userId) {
        List<ScreenShots> screenShots = screenShotsDao.getScreenShotsByUser(userId);
        Pager<ScreenShots> pager = new Pager<>(pageNum,10,screenShots);
        return pager;
    }

}
