package com.monitoring.service;

import com.monitoring.entity.ScreenShots;
import com.monitoring.util.Pager;

import java.util.List;

public interface ScreenShotsService {
    public ScreenShots getScreenShotsById(int shotsId);

    public ScreenShots getScreenShotsByMonitorId(int monitorId);

    public ScreenShots getScreenShotsByName(String name);

    public boolean addScreenShots(ScreenShots screenShots);

    public boolean deleteScreenShotsById(int shotsId);
    
    public boolean deleteScreenShotsByUserId(int userId);

    public void saveOrupdate(ScreenShots screenShots);

    public List<ScreenShots> getScreenShots();

    public List<ScreenShots> getScreenShotsPageList(int pageNum);

    public int pageS();

    public boolean updateScreenShots(ScreenShots screenShots);

    public Pager<ScreenShots> getUserScreenShots(int pageNum,int monitorId);

    public List<ScreenShots> getUserScreenShots(int monitorId);

    public Pager<ScreenShots> getScreenShotsByUser(int pageNum, int userId);
}
