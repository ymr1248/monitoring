package com.monitoring.service;

import java.util.List;

import com.monitoring.entity.Space;
import com.monitoring.entity.SpaceOrder;
import com.monitoring.util.Pager;

/**
 * @author mry
 *2018年1月2日 上午11:24:36
 */
public interface SpaceService {
	public Space getSpaceById(int id);

	public Space getSpaceByUserId(int userId);

	public Space getSpaceByName(String name);

	List<Space> getSpaceByNameLike(String name);
	
	public void addSpace(Space space);

	public boolean addSpaceOrder(SpaceOrder spaceOrder);

	public boolean deleteSpaceById(int id);
	
	public boolean deleteSpaceByUserId(int userId);

	public void saveOrupdate(Space space);

	public List<Space> getSpaces();

	public Pager<Space> getSpacesPageList(int pageNum);

	public Pager<SpaceOrder> getSpacesOrderPageList(int pageNum);

	public int pageS();

	public boolean isOrNadd(int id);

	public boolean updateSpace(Space space);
}
