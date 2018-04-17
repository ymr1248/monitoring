package com.monitoring.dao;

import com.monitoring.entity.ScreenShots;

import java.util.List;

public interface ScreenShotsDao {
    public ScreenShots getScreenShotsById(int shotsId);

    public ScreenShots getScreenShotsByMonitorId(int monitorId);

    public ScreenShots getScreenShotsByName(String name);

    public int addScreenShots(ScreenShots screenShots);

    public int deleteScreenShotsById(int shotsId);
    
    public int deleteScreenShotsByUserId(int userId);

    public void saveOrupdate(ScreenShots screenShots);

    public List<ScreenShots> getScreenShots();

    public List<ScreenShots> getScreenShotsPageList(int pageNum);

    public int pageS();


    public int updateScreenShots(ScreenShots screenShots);

    public List<ScreenShots> getUserScreenShotsByMonitor(int monitorId);

    public List<ScreenShots> getScreenShotsByUser(int userId);

}
