package com.demo.service;

import com.demo.entity.OrderEntity;

/**
 * @author fenqing
 */
public interface IOrderService {
    /**
     * update
     * @param order order
     */
    void update(OrderEntity order);

    /**
     * info
     * @param orderId orderId
     * @return return
     */
    OrderEntity info(Long orderId);
}
