package com.monitoring.dao;


import com.monitoring.entity.Space;
import com.monitoring.entity.SpaceOrder;

import java.util.List;

/**
 * @author mry
 *2018年1月2日 上午11:17:03
 */
public interface SpaceOrderDao {
	public SpaceOrder getSpaceOrderById(int id);

	public List<SpaceOrder> getSpaceOrderBySpace(Space space);

	public SpaceOrder getSpaceOrderByName(String name);

	public void addSpaceOrder(SpaceOrder spaceOrder);

	public int deleteSpaceOrderById(int id);
	
	public int deleteSpaceOrderBySpaceId(int spaceId);

	public void saveOrupdate(SpaceOrder spaceOrder);

	public List<SpaceOrder> getSpaceOrders();

	public List<SpaceOrder> getSpaceOrdersPageList(int pageNum);

	public int pageS();

	public Integer isOrNadd(int id);

	public Integer updateSpaceOrder(SpaceOrder spaceOrder);
}
