package com.demo.service.impl;

import com.demo.dao.OrderDao;
import com.demo.entity.OrderEntity;
import com.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fenqing
 */
@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void update(OrderEntity order) {
        orderDao.update(order);
    }

    @Override
    public OrderEntity info(Long orderId) {
        return orderDao.selectOneById(orderId);
    }
}
