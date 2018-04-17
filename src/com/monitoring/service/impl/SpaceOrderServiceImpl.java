package com.monitoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monitoring.dao.SpaceOrderDao;
import com.monitoring.entity.Space;
import com.monitoring.entity.SpaceOrder;
import com.monitoring.service.SpaceOrderService;

@Service
public class SpaceOrderServiceImpl implements SpaceOrderService {

	@Autowired
	SpaceOrderDao spaceOrderDao;
	
	@Override
	public List<SpaceOrder> getSpaceOrdersBySpaceId(List<Space> list) {
		List<SpaceOrder> list2 = new ArrayList<SpaceOrder>();
		for (int i = 0; i < list.size(); i++) {
			List<SpaceOrder> list3 = spaceOrderDao.getSpaceOrderBySpace(list.get(i));
			for (int j = 0; j < list3.size(); j++) {
				list2.add(list3.get(j));
			}
			
		}
		return list2;
	}

}
