package com.demo.dao;

import com.demo.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author fenqing
 */
@Repository
public interface OrderDao {
    /**
     * a
     * @param order a
     */
    void update(OrderEntity order);

    /**
     * a
     * @param orderId a
     * @return a
     */
    OrderEntity selectOneById(@Param("orderId") Long orderId);

    /**
     * a
     * @param userId a
     * @param username a
     */
    void updateUserName(@Param("userId") Long userId, @Param("username") String username);
}
