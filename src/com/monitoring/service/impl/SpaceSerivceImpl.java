package com.monitoring.service.impl;

import java.util.List;
import com.monitoring.dao.SpaceOrderDao;
import com.monitoring.entity.SpaceOrder;
import com.monitoring.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.dao.SpaceDao;
import com.monitoring.entity.Space;
import com.monitoring.service.SpaceService;

/**
 * @author mry
 *2018年1月2日 上午11:26:53
 */
@Service
public class SpaceSerivceImpl implements SpaceService {
	@Autowired
	SpaceDao spaceDao;
	@Autowired
	SpaceOrderDao spaceOrderDao;

	@Override
	public Space getSpaceById(int id) {
		return spaceDao.getSpaceById(id);
	}

	@Override
	public Space getSpaceByUserId(int userId) {
		return spaceDao.getSpaceByUserId(userId);
	}

	@Override
	public Space getSpaceByName(String name) {
		return spaceDao.getSpaceByName(name);
	}

	@Override
	public List<Space> getSpaceByNameLike(String name) {
		return spaceDao.getSpaceByNameLike(name);
	}

	@Override
	public void addSpace(Space space) {
		spaceDao.addSpace(space);
	}

	@Override
	public boolean addSpaceOrder(SpaceOrder spaceOrder) {
		spaceOrderDao.addSpaceOrder(spaceOrder);
		String extraSize = Integer.parseInt(spaceOrder.getExtraSize()) + Integer.parseInt(spaceOrder.getSpace().getExtraSize())+"";
		spaceDao.updateSpaceExtraSize(extraSize, spaceOrder.getSpace().getId());
		return true;
	}

	@Override
	public boolean deleteSpaceById(int id) {
		if (spaceDao.deleteSpaceById(id) == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean deleteSpaceByUserId(int userId) {
		Space space = spaceDao.getSpaceByUserId(userId);
		if(space!=null){
			int spaceId = space.getId();
			spaceOrderDao.deleteSpaceOrderBySpaceId(spaceId);
			if (spaceDao.deleteSpaceByUserId(userId) == 1) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void saveOrupdate(Space space) {
		spaceDao.saveOrupdate(space);
	}

	@Override
	public List<Space> getSpaces() {
		return spaceDao.getSpaces();
	}

	@Override
	public Pager<Space> getSpacesPageList(int pageNum) {
		List<Space> spaceList = spaceDao.getSpaces();
		Pager<Space> pager = new Pager<>(pageNum, 10, spaceList);
		return pager;
	}

	@Override
	public Pager<SpaceOrder> getSpacesOrderPageList(int pageNum) {
		List<SpaceOrder> spaceOrderList = spaceOrderDao.getSpaceOrders();
		Pager<SpaceOrder> pager = new Pager<>(pageNum, 10, spaceOrderList);
		return pager;
	}

	@Override
	public int pageS() {
		return spaceDao.pageS();
	}

	@Override
	public boolean isOrNadd(int id) {
		return false;
	}

	@Override
	public boolean updateSpace(Space space) {
		if (spaceDao.updateSpace(space) == 1) {
			return true;
		}
		return false;
	}
}
