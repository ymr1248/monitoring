package com.monitoring.service;

import java.util.List;

import com.monitoring.entity.Space;
import com.monitoring.entity.SpaceOrder;

public interface SpaceOrderService {

	List<SpaceOrder> getSpaceOrdersBySpaceId(List<Space> list);
}
